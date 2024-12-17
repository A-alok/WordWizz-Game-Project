import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class WordGuessGame {
    private String correctWord;
    private final int ROWS = 5;
    private final int COLS = 5;
    private JTextField[][] grid;
    private JPanel gridPanel;
    private JFrame frame = this.createAndShowGUI();
    private int currentRow = 0;
    private int currentCol = 0;

    private final String[] predefinedWords = {
            "APPLE", "BREAD", "CRISP", "DREAM", "EARTH", "FROST", "GRAPE", "HOUSE", "INPUT", "JOKER",
            "KNIFE", "LIGHT", "MOUSE", "NURSE", "OLIVE", "PIZZA", "QUIET", "ROBIN", "STONE", "TIGER",
            "UNITE", "VIOLET", "WATER", "XENON", "YEAST", "ZEBRA", "ACTOR", "BIRCH", "CANDY", "DRILL",
            "ELITE", "FLAME", "GHOST", "HEART", "INDEX", "JAZZY", "KAYAK", "LEMON", "MINTY", "NIGHT",
            "OPERA", "PILOT", "QUACK", "RIVER", "SWORD", "TRUCK", "UNITY", "VOICE", "WORST", "XYLEM",
            "YOUTH", "ZORRO", "ANGEL", "BLINK", "CRANE", "DRAFT", "EAGLE", "FUDGE", "GRIND", "HUMOR",
            "IRONY", "JELLY", "KOALA", "LEAFY", "MANGO", "NINJA", "OMEGA", "PEARL", "QUILT", "RADAR",
            "SHAPE", "THUMB", "UNDER", "VAGUE", "WHALE", "XENIA", "YEARN", "ZEBRA", "AGENT", "BLUFF",
            "CHEST", "DELTA", "EPOCH", "FIELD", "GIANT", "HORSE", "IMAGE", "JOINT", "KNEEL", "LUNAR"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGuessGame::new);
    }

    public WordGuessGame() {
        this.startNewGame();
    }

    private void startNewGame() {
        this.correctWord = this.fetchRandomWord().toUpperCase();
        this.grid = new JTextField[5][5];
        this.initializeGrid();
        this.clearGrid();
        this.currentRow = 0;
        this.currentCol = 0;
        this.updateGridPanel();
    }

    private String fetchRandomWord() {
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word?number=1&length=5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonResponse.append(inputLine);
            }
            in.close();
            String word = jsonResponse.toString();
            word = word.replaceAll("[\\[\\]\"]", "");
            return word;
        } catch (Exception e) {
            // If the fetch fails, use a random word from predefinedWords
            int randomIndex = (int) (Math.random() * predefinedWords.length);
            return predefinedWords[randomIndex];
        }
    }

    private void initializeGrid() {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.grid[i][j] = new JTextField();
                this.grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                this.grid[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                this.grid[i][j].setEditable(false);
                this.grid[i][j].setFocusable(false); // Prevent focusing on text fields
                // Prevent mouse clicks from affecting the game
                this.grid[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        evt.consume(); // Ignore the mouse click event
                    }
                });
            }
        }
    }

    private void clearGrid() {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.grid[i][j].setText("");
                this.grid[i][j].setBackground(Color.WHITE);
            }
        }
    }

    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("Word Guess Game");
        ImageIcon favicon = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\W__1_-removebg-preview.png");
        frame.setIconImage(favicon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\vinay\\Downloads\\miniiiiiiiiii\\miniiiiiiiiii\\icon\\front page.jpg");
                Image image = backgroundImage.getImage();
                int panelWidth = this.getWidth();
                int panelHeight = this.getHeight();
                double widthRatio = (double) panelWidth / (double) image.getWidth(null);
                double heightRatio = (double) panelHeight / (double) image.getHeight(null);
                double scaleFactor = Math.max(widthRatio, heightRatio);
                int newWidth = (int) (image.getWidth(null) * scaleFactor);
                int newHeight = (int) (image.getHeight(null) * scaleFactor);
                int x = (panelWidth - newWidth) / 2;
                int y = (panelHeight - newHeight) / 2;
                g.drawImage(image, x, y, newWidth, newHeight, this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());
        this.gridPanel = new JPanel();
        this.gridPanel.setLayout(new GridLayout(5, 5, 10, 10));
        this.gridPanel.setOpaque(false);
        this.gridPanel.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80));
        backgroundPanel.add(this.gridPanel, BorderLayout.CENTER);

        frame.add(backgroundPanel);
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLetter(keyChar)) {
                    WordGuessGame.this.processLetterInput(Character.toString(Character.toUpperCase(keyChar)));
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    WordGuessGame.this.processBackspace();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    WordGuessGame.this.processEnter();
                }
            }
        });
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        return frame;
    }

    private void updateGridPanel() {
        this.gridPanel.removeAll();
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.gridPanel.add(this.grid[i][j]);
            }
        }
        this.gridPanel.revalidate();
        this.gridPanel.repaint();
    }

    private void processLetterInput(String letter) {
        if (this.currentRow < 5 && this.currentCol < 5) {
            this.grid[this.currentRow][this.currentCol].setText(letter);
            ++this.currentCol;
            if (this.currentCol == 5) {
                this.processEnter();
            }
        }
    }

    private void processBackspace() {
        if (this.currentCol > 0) {
            --this.currentCol;
            this.grid[this.currentRow][this.currentCol].setText("");
        }
    }

    private void processEnter() {
        if (this.currentCol == 5) {
            StringBuilder guess = new StringBuilder();
            for (int i = 0; i < 5; ++i) {
                guess.append(this.grid[this.currentRow][i].getText());
            }

            String guessWord = guess.toString().toUpperCase();
            if (guessWord.equals(this.correctWord)) {
                // All letters are correct, color them green and display success message
                for (int i = 0; i < 5; ++i) {
                    this.grid[this.currentRow][i].setBackground(Color.GREEN);
                }

                // Show popup with "Play Again" and "Main Menu" options
                int choice = JOptionPane.showOptionDialog(this.frame,
                        "Congratulations! You guessed the word! Would you like to play again?",
                        "You Win!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Play Again", "Main Menu"},
                        "Play Again");

                if (choice == JOptionPane.YES_OPTION) {
                    this.startNewGame(); // Restart the game
                } else {
                    this.frame.dispose(); // Close the game
                    new WordWizzGUI(); // Redirect to the main menu
                }

            } else {
                // Process feedback for each letter
                char[] correctWordChars = this.correctWord.toCharArray();
                boolean[] correctPositions = new boolean[5]; // Track correct positions

                // First pass: check for correct letters in correct positions
                for (int i = 0; i < 5; ++i) {
                    if (guessWord.charAt(i) == correctWordChars[i]) {
                        this.grid[this.currentRow][i].setBackground(Color.GREEN); // Correct position
                        correctPositions[i] = true; // Mark this position as correct
                        correctWordChars[i] = '-';  // Mark as used in the correct word
                    } else {
                        this.grid[this.currentRow][i].setBackground(Color.GRAY); // Default to incorrect
                    }
                }

                // Second pass: check for correct letters in wrong positions
                for (int i = 0; i < 5; ++i) {
                    if (!correctPositions[i]) { // Only check if not already correct
                        char currentLetter = guessWord.charAt(i);
                        for (int j = 0; j < 5; ++j) {
                            if (correctWordChars[j] == currentLetter) {
                                this.grid[this.currentRow][i].setBackground(Color.YELLOW); // Wrong position
                                correctWordChars[j] = '-'; // Mark as used
                                break;
                            }
                        }
                    }
                }

                // Move to the next row or end the game if out of guesses
                if (this.currentRow < 4) {
                    ++this.currentRow;
                    this.currentCol = 0;
                } else {
                    // Show popup with "Play Again" and "Main Menu" options when the game ends
                    int choice = JOptionPane.showOptionDialog(this.frame,
                            "Game Over! The word was: " + this.correctWord + ". Would you like to play again?",
                            "Game Over",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{"Play Again", "Main Menu"},
                            "Play Again");

                    if (choice == JOptionPane.YES_OPTION) {
                        this.startNewGame(); // Restart the game
                    } else {
                        this.frame.dispose(); // Close the game
                        new WordWizzGUI(); // Redirect to the main menu
                    }
                }
            }
        }
    }
}
