import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game2048GUI extends JFrame {
    private Game_2048_Adv2 game;
    private JPanel boardPanel;
    private JLabel[][] tileLabels;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    public Game2048GUI() {
        game = new Game_2048_Adv2();
        initializeUI();
        updateUI();
    }

    private void initializeUI() {
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardPanel.setBackground(new Color(187, 173, 160));

        tileLabels = new JLabel[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tileLabels[i][j] = new JLabel("", SwingConstants.CENTER);
                tileLabels[i][j].setOpaque(true);
                tileLabels[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                tileLabels[i][j].setPreferredSize(new Dimension(80, 80)); // Ensure proper sizing
                boardPanel.add(tileLabels[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel(new FlowLayout());
        scoreLabel = new JLabel("Score: 0");
        highScoreLabel = new JLabel("High Score: 0");
        scorePanel.add(scoreLabel);
        scorePanel.add(highScoreLabel);
        add(scorePanel, BorderLayout.NORTH);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        game.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        game.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        game.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.moveRight();
                        break;
                    case KeyEvent.VK_U:
                        game.undo();
                        break;
                }
                updateUI();
            }
        });

        setFocusable(true);
    }

    public void updateUI() {
        int[][] board = game.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = board[i][j];
                tileLabels[i][j].setText(value == 0 ? "" : String.valueOf(value));
                tileLabels[i][j].setBackground(getTileColor(value));
                tileLabels[i][j].setForeground(value < 8 ? new Color(119, 110, 101) : Color.WHITE);
            }
        }
        scoreLabel.setText("Score: " + game.getCurrentScore());
        highScoreLabel.setText("High Score: " + game.getHighScore());

        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over! Your score: " + game.getCurrentScore());
            game.resetGame();
            updateUI();
        } else if (game.hasWon()) {
            JOptionPane.showMessageDialog(this, "Congratulations! You've reached 2048!");
        }

        repaint();
    }

    private Color getTileColor(int value) {
        switch (value) {
            case 2:    return new Color(238, 228, 218);
            case 4:    return new Color(237, 224, 200);
            case 8:    return new Color(242, 177, 121);
            case 16:   return new Color(245, 149, 99);
            case 32:   return new Color(246, 124, 95);
            case 64:   return new Color(246, 94, 59);
            case 128:  return new Color(237, 207, 114);
            case 256:  return new Color(237, 204, 97);
            case 512:  return new Color(237, 200, 80);
            case 1024: return new Color(237, 197, 63);
            case 2048: return new Color(237, 194, 46);
            default:   return new Color(205, 193, 180);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game2048GUI gui = new Game2048GUI();
            gui.setVisible(true);
        });
    }
}