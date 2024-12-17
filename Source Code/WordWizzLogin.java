import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class WordWizzLogin extends JFrame {

    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JLabel eyeIconLabel;
    private boolean isPasswordVisible = false;  // Track password visibility

    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/wordwizz"; // Replace with your database URL
    private final String DB_USERNAME = "root"; // Replace with your database username
    private final String DB_PASSWORD = "Netizun@26"; // Replace with your database password

    public WordWizzLogin() {
        // Set JFrame properties
        setTitle("WordWizz Login");
        setSize(600, 600); // Changed to 600x600
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        getContentPane().setBackground(new Color(13, 111, 108)); // Teal background color

        // Add the favicon logo for the frame's title bar
        ImageIcon favicon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\W__1_-removebg-preview.png");  // Replace with your favicon image path
        setIconImage(favicon.getImage());  // Set the favicon for the JFrame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Make the fields fill horizontally
        gbc.weightx = 1.0;  // Allow the text fields to take space
        gbc.anchor = GridBagConstraints.WEST;

        // Load the custom font (Roboto Slab)
        Font robotoSlab = new Font("Roboto Slab", Font.PLAIN, 16);

        // Image Logo
        JLabel imageLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\W__1_-removebg-preview.png");  // Replace with your image path
        Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        add(imageLabel, gbc);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(robotoSlab.deriveFont(18f));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(robotoSlab);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(robotoSlab.deriveFont(18f));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(robotoSlab);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Eye icon for toggling password visibility
        eyeIconLabel = new JLabel();
        ImageIcon eyeIcon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\icons8_eye_20px_1.png"); // Replace with your eye icon image path
        Image scaledEyeIcon = eyeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        eyeIconLabel.setIcon(new ImageIcon(scaledEyeIcon));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(eyeIconLabel, gbc);

        // Add mouse listener to toggle password visibility when clicking the eye icon
        eyeIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                togglePasswordVisibility();
            }
        });

        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(robotoSlab.deriveFont(16f));
        loginButton.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Login action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Register Button
        registerButton = new JButton("REGISTER");
        registerButton.setFont(robotoSlab.deriveFont(16f));
        registerButton.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Register action listener to open registration form
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });

        setVisible(true);
    }

    // Method to toggle password visibility
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordField.setEchoChar('*');
            isPasswordVisible = false;
        } else {
            passwordField.setEchoChar((char) 0);
            isPasswordVisible = true;
        }
    }

    // Login method with database verification
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful! Launching WordWizz...");
                new Instruction(); // Call WordWizz GUI (you need to implement this part)
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login failed! Invalid username or password.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.");
        }
    }

    // Method to open the registration form with the same layout as the login form
    private void openRegistrationForm() {
        JFrame registrationFrame = new JFrame("Register New User");
        registrationFrame.setSize(600, 600);
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setLocationRelativeTo(null);
        registrationFrame.setLayout(new GridBagLayout());
        registrationFrame.getContentPane().setBackground(new Color(13, 111, 108));

        ImageIcon favicon = new ImageIcon("CC:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\W__1_-removebg-preview.png654w3");
        registrationFrame.setIconImage(favicon.getImage());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font robotoSlab = new Font("Roboto Slab", Font.PLAIN, 16);

        JLabel imageLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\W__1_-removebg-preview.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        registrationFrame.add(imageLabel, gbc);

        JLabel newUsernameLabel = new JLabel("New Username:");
        newUsernameLabel.setForeground(Color.WHITE);
        newUsernameLabel.setFont(robotoSlab.deriveFont(18f));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registrationFrame.add(newUsernameLabel, gbc);

        JTextField newUsernameField = new JTextField(20);
        newUsernameField.setFont(robotoSlab);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registrationFrame.add(newUsernameField, gbc);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setForeground(Color.WHITE);
        newPasswordLabel.setFont(robotoSlab.deriveFont(18f));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registrationFrame.add(newPasswordLabel, gbc);

        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordField.setFont(robotoSlab);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registrationFrame.add(newPasswordField, gbc);

        JButton registerNewUserButton = new JButton("REGISTER");
        registerNewUserButton.setFont(robotoSlab.deriveFont(16f));
        registerNewUserButton.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registrationFrame.add(registerNewUserButton, gbc);

        registerNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

                    stmt.setString(1, newUsername);
                    stmt.setString(2, newPassword);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(registrationFrame, "Registration successful!");
                    registrationFrame.dispose();
                    new WordWizzLogin();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(registrationFrame, "Database error. Could not register user.");
                }
            }
        });

        registrationFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordWizzLogin());
    }
}
