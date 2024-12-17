import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordWizzGUI extends JFrame {
    private Image backgroundImage;

    public WordWizzGUI() {
        // Load the background image
        backgroundImage = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\E (4).png").getImage(); // Set your background image path

        // Create a panel with a custom background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the image to fit the panel
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding between components

        // Title - "WORDWIZZ"
        JLabel titleLabel = new JLabel("WORDWIZZ");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 139)); // Dark blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(titleLabel, gbc);

        // Subtitle - "Guess the Word"
        JLabel subtitleLabel = new JLabel("Guess The Word");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(0, 100, 0));  // Dark green color
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        backgroundPanel.add(subtitleLabel, gbc);

        // Play Button
        JButton playButton = createRoundedButton("PLAY");
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        backgroundPanel.add(playButton, gbc);

        // Theme Label - "THEME"
        JLabel themeLabel = new JLabel("THEME");
        themeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(themeLabel, gbc);

        // Theme buttons (Animal, Veggies, Fruits)
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Organize buttons in a horizontal flow
        themePanel.setOpaque(false); // Transparent background for panel

        // Animal Button with Image
        JButton btnAnimal = createRoundedButton("Animal");
        ImageIcon animalIcon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\logo animal.png");
        btnAnimal.setIcon(animalIcon);
        btnAnimal.setHorizontalTextPosition(SwingConstants.RIGHT);
        themePanel.add(btnAnimal);

        // Veggies Button with Image
        JButton btnVeggies = createRoundedButton("Veggies");
        ImageIcon veggiesIcon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\logo veggi.png");
        btnVeggies.setIcon(veggiesIcon);
        btnVeggies.setHorizontalTextPosition(SwingConstants.RIGHT);
        themePanel.add(btnVeggies);

        // Fruits Button with Image
        JButton btnFruits = createRoundedButton("Fruits");
        ImageIcon fruitsIcon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\logo fruit.png");
        btnFruits.setIcon(fruitsIcon);
        btnFruits.setHorizontalTextPosition(SwingConstants.RIGHT);
        themePanel.add(btnFruits);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        backgroundPanel.add(themePanel, gbc);

        // Add action listeners for buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to WordGuessGame
                dispose(); // Close the current frame
                new WordGuessGame(); // Start the Word Guess Game
            }
        });

        btnAnimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to Animal Game (example)
                dispose(); // Close the current frame
                new AnimalGrid(); // Start the Animal Game (replace with your class)
            }
        });

        btnVeggies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to VegetableGrid
                dispose(); // Close the current frame
                new VegetableGrid(); // Start the Vegetable Game
            }
        });

        btnFruits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to FruitGrid
                dispose(); // Close the current frame
                new FruitGrid(); // Start the Fruit Game
            }
        });

        // Set frame properties
        setContentPane(backgroundPanel); // Set the background panel as the content pane
        setTitle("WordWizz");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to create buttons with rounded edges
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Enable anti-aliasing
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);  // Create rounded rectangle
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            public void setContentAreaFilled(boolean b) {
                // Do nothing to prevent default background painting
            }
        };
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLUE);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        return button;
    }

    public static void main(String[] args) {
        // Set the look and feel to match the system theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the GUI
        new WordWizzGUI();
    }
}
