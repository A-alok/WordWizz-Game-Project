import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Instruction extends JFrame {
        private Image splashImage;

        public Instruction() {
            // Load the splash image
            splashImage = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\Instruction page .png").getImage();

            // Create a panel for the splash screen
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(splashImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            panel.setLayout(new BorderLayout());

            // Create the Start button
            JButton startButton = new JButton("Start");
            startButton.setFont(new Font("Arial", Font.BOLD, 20));
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Close the splash screen
                    new WordWizzGUI(); // Open the main game window
                }
            });

            // Add the button to the panel
            panel.add(startButton, BorderLayout.SOUTH);
            panel.setPreferredSize(new Dimension(500, 600)); // Set the size as needed

            // Set up the frame
            setContentPane(panel);
            setTitle("Welcome to WordWizz");
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        public static void main(String[] args) {
            // Set the look and feel to match the system theme
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Show the splash screen
            new Instruction();
        }
    }

