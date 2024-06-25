import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game_2048 extends JPanel {
    private static final int SIZE = 4;
    private static final int TILE_SIZE = 80;
    private static final int MARGIN = 10;
    private static final Color BG_COLOR = new Color(0xbbada0);

    private int[][] board;
    private boolean win;
    private boolean gameOver;

    public Game_2048() {
        setPreferredSize(new Dimension(SIZE * (TILE_SIZE + MARGIN) + MARGIN, SIZE * (TILE_SIZE + MARGIN) + MARGIN));
        setBackground(BG_COLOR);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!win && !gameOver) {
                    boolean moved = false;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:    moved = moveUp(); break;
                        case KeyEvent.VK_DOWN:  moved = moveDown(); break;
                        case KeyEvent.VK_LEFT:  moved = moveLeft(); break;
                        case KeyEvent.VK_RIGHT: moved = moveRight(); break;
                    }
                    if (moved) {
                        addTile();
                        if (!canMove()) {
                            gameOver = true;
                        }
                    }
                    repaint();
                }
            }
        });
        resetGame();
    }

    private void resetGame() {
        board = new int[SIZE][SIZE];
        win = false;
        gameOver = false;
        addTile();
        addTile();
    }

    private void addTile() {
        if (isBoardFull()) return;

        int x, y;
        do {
            x = (int) (Math.random() * SIZE);
            y = (int) (Math.random() * SIZE);
        } while (board[x][y] != 0);

        board[x][y] = Math.random() < 0.9 ? 2 : 4;
    }

    private boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) return false;
            }
        }
        return true;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int merge = 0;
            for (int i = 1; i < SIZE; i++) {
                if (board[i][j] != 0) {
                    int row = i;
                    while (row > merge && board[row - 1][j] == 0) {
                        board[row - 1][j] = board[row][j];
                        board[row][j] = 0;
                        row--;
                        moved = true;
                    }
                    if (row > merge && board[row - 1][j] == board[row][j]) {
                        board[row - 1][j] *= 2;
                        board[row][j] = 0;
                        merge = row;
                        moved = true;
                        if (board[row - 1][j] == 2048) win = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int merge = SIZE - 1;
            for (int i = SIZE - 2; i >= 0; i--) {
                if (board[i][j] != 0) {
                    int row = i;
                    while (row < merge && board[row + 1][j] == 0) {
                        board[row + 1][j] = board[row][j];
                        board[row][j] = 0;
                        row++;
                        moved = true;
                    }
                    if (row < merge && board[row + 1][j] == board[row][j]) {
                        board[row + 1][j] *= 2;
                        board[row][j] = 0;
                        merge = row;
                        moved = true;
                        if (board[row + 1][j] == 2048) win = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int merge = 0;
            for (int j = 1; j < SIZE; j++) {
                if (board[i][j] != 0) {
                    int col = j;
                    while (col > merge && board[i][col - 1] == 0) {
                        board[i][col - 1] = board[i][col];
                        board[i][col] = 0;
                        col--;
                        moved = true;
                    }
                    if (col > merge && board[i][col - 1] == board[i][col]) {
                        board[i][col - 1] *= 2;
                        board[i][col] = 0;
                        merge = col;
                        moved = true;
                        if (board[i][col - 1] == 2048) win = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int merge = SIZE - 1;
            for (int j = SIZE - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int col = j;
                    while (col < merge && board[i][col + 1] == 0) {
                        board[i][col + 1] = board[i][col];
                        board[i][col] = 0;
                        col++;
                        moved = true;
                    }
                    if (col < merge && board[i][col + 1] == board[i][col]) {
                        board[i][col + 1] *= 2;
                        board[i][col] = 0;
                        merge = col;
                        moved = true;
                        if (board[i][col + 1] == 2048) win = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean canMove() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return true;
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) return true;
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int x = j * (TILE_SIZE + MARGIN) + MARGIN;
                int y = i * (TILE_SIZE + MARGIN) + MARGIN;
                drawTile(g2d, board[i][j], x, y);
            }
        }

        if (win || gameOver) {
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            String message = win ? "You Win!" : "Game Over!";
            g2d.drawString(message, getWidth() / 2 - 70, getHeight() / 2);
        }
    }

    private void drawTile(Graphics2D g2d, int value, int x, int y) {
        g2d.setColor(getTileColor(value));
        g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
        g2d.setColor(value < 128 ? Color.BLACK : Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String s = String.valueOf(value);
        if (value != 0) {
            FontMetrics fm = g2d.getFontMetrics();
            int asc = fm.getAscent();
            int dec = fm.getDescent();
            int sw = fm.stringWidth(s);
            int x1 = x + (TILE_SIZE - sw) / 2;
            int y1 = y + (asc + (TILE_SIZE - (asc + dec)) / 2);
            g2d.drawString(s, x1, y1);
        }
    }

    private Color getTileColor(int value) {
        switch (value) {
            case 2:    return new Color(0xEEE4DA);
            case 4:    return new Color(0xEDE0C8);
            case 8:    return new Color(0xF2B179);
            case 16:   return new Color(0xF59563);
            case 32:   return new Color(0xF67C5F);
            case 64:   return new Color(0xF65E3B);
            case 128:  return new Color(0xEDCF72);
            case 256:  return new Color(0xEDCC61);
            case 512:  return new Color(0xEDC850);
            case 1024: return new Color(0x41C7A9);
            case 2048: return new Color(0x3C3A32);
            default:   return new Color(0xCDC1B4);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Game_2048());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}