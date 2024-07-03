import java.util.*;

public class Game_2048_Adv2 {
    private static final int SIZE = 4;
    private static int[][] board = new int[SIZE][SIZE];
    private static boolean win = false;
    private static Stack<GameState> undoStack = new Stack<>();
    private static Map<String, Integer> boardOccurrences = new HashMap<>();
    private static int currentScore = 0;
    private static int highScore = 0;

    private static class GameState {
        int[][] board;
        int score;

        GameState(int[][] board, int score) {
            this.board = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                this.board[i] = Arrays.copyOf(board[i], SIZE);
            }
            this.score = score;
        }
    }

    public Game_2048_Adv2() {
        resetGame();
    }

    public static void resetGame() {
        board = new int[SIZE][SIZE];
        undoStack.clear();
        boardOccurrences.clear();
        currentScore = 0;
        win = false;
        addTile();
        addTile();
        updateBoardOccurrences();
    }

    private static void saveState() {
        undoStack.push(new GameState(board, currentScore));
    }

    public static void undo() {
        if (!undoStack.isEmpty()) {
            GameState previousState = undoStack.pop();
            board = previousState.board;
            currentScore = previousState.score;
            updateBoardOccurrences();
        } else {
            System.out.println("No moves to undo.");
        }
    }

    private static void updateBoardOccurrences() {
        String boardState = Arrays.deepToString(board);
        boardOccurrences.put(boardState, boardOccurrences.getOrDefault(boardState, 0) + 1);
    }

    private static void addTile() {
        int x, y;
        do {
            x = (int) (Math.random() * SIZE);
            y = (int) (Math.random() * SIZE);
        } while (board[x][y] != 0);
        board[x][y] = Math.random() < 0.9 ? 2 : 4;
        updateBoardOccurrences();
    }

    public static void moveUp() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                if (board[i][j] != 0) {
                    for (int k = i; k > 0; k--) {
                        if (board[k - 1][j] == 0) {
                            board[k - 1][j] = board[k][j];
                            board[k][j] = 0;
                            moved = true;
                        } else if (board[k - 1][j] == board[k][j]) {
                            board[k - 1][j] *= 2;
                            currentScore += board[k - 1][j];
                            board[k][j] = 0;
                            if (board[k - 1][j] == 2048) {
                                win = true;
                            }
                            moved = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        if (moved) {
            addTile();
            updateBoardOccurrences();
            highScore = Math.max(highScore, currentScore);
        }
    }

    public static void moveDown() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 2; i >= 0; i--) {
                if (board[i][j] != 0) {
                    for (int k = i; k < SIZE - 1; k++) {
                        if (board[k + 1][j] == 0) {
                            board[k + 1][j] = board[k][j];
                            board[k][j] = 0;
                            moved = true;
                        } else if (board[k + 1][j] == board[k][j]) {
                            board[k + 1][j] *= 2;
                            currentScore += board[k + 1][j];
                            board[k][j] = 0;
                            if (board[k + 1][j] == 2048) {
                                win = true;
                            }
                            moved = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        if (moved) {
            addTile();
            updateBoardOccurrences();
            highScore = Math.max(highScore, currentScore);
        }
    }

    public static void moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (board[i][j] != 0) {
                    for (int k = j; k > 0; k--) {
                        if (board[i][k - 1] == 0) {
                            board[i][k - 1] = board[i][k];
                            board[i][k] = 0;
                            moved = true;
                        } else if (board[i][k - 1] == board[i][k]) {
                            board[i][k - 1] *= 2;
                            currentScore += board[i][k - 1];
                            board[i][k] = 0;
                            if (board[i][k - 1] == 2048) {
                                win = true;
                            }
                            moved = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        if (moved) {
            addTile();
            updateBoardOccurrences();
            highScore = Math.max(highScore, currentScore);
        }
    }

    public static void moveRight() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    for (int k = j; k < SIZE - 1; k++) {
                        if (board[i][k + 1] == 0) {
                            board[i][k + 1] = board[i][k];
                            board[i][k] = 0;
                            moved = true;
                        } else if (board[i][k + 1] == board[i][k]) {
                            board[i][k + 1] *= 2;
                            currentScore += board[i][k + 1];
                            board[i][k] = 0;
                            if (board[i][k + 1] == 2048) {
                                win = true;
                            }
                            moved = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        if (moved) {
            addTile();
            updateBoardOccurrences();
            highScore = Math.max(highScore, currentScore);
        }
    }

    private static boolean canMove() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) {
                    return true;
                }
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public boolean isGameOver() {
        return !canMove();
    }

    public boolean hasWon() {
        return win;
    }
}
