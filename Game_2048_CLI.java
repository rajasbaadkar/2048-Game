import java.util.Scanner;

public class Game_2048_CLI {
    private static final int SIZE = 4;
    private static int[][] board = new int[SIZE][SIZE];
    private static boolean win = false;

    public static void main(String[] args) {
        resetGame();
        Scanner scanner = new Scanner(System.in);
        while (!win) {
            printBoard();
            System.out.print("Enter direction (W/A/S/D): ");
            String input = scanner.next().toUpperCase();
            switch (input) {
                case "W":
                    moveUp();
                    break;
                case "A":
                    moveLeft();
                    break;
                case "S":
                    moveDown();
                    break;
                case "D":
                    moveRight();
                    break;
                default:
                    System.out.println("Invalid input. Please enter W/A/S/D.");
            }
            if (!canMove()) {
                System.out.println("Game over!");
                break;
            }
        }
        scanner.close();
    }

    private static void resetGame() {
        board = new int[SIZE][SIZE];
        addTile();
        addTile();
    }

    private static void addTile() {
        int x, y;
        do {
            x = (int) (Math.random() * SIZE);
            y = (int) (Math.random() * SIZE);
        } while (board[x][y] != 0);
        board[x][y] = Math.random() < 0.9 ? 2 : 4;
    }

    private static void printBoard() {
        for (int[] row : board) {
            for (int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    private static void moveUp() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                for (int k = i; k > 0; k--) {
                    if (board[k - 1][j] == 0) {
                        board[k - 1][j] = board[k][j];
                        board[k][j] = 0;
                    } else if (board[k - 1][j] == board[k][j]) {
                        board[k - 1][j] *= 2;
                        board[k][j] = 0;
                        if (board[k - 1][j] == 2048) {
                            win = true;
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        addTile();
    }

    private static void moveDown() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 2; i >= 0; i--) {
                for (int k = i; k < SIZE - 1; k++) {
                    if (board[k + 1][j] == 0) {
                        board[k + 1][j] = board[k][j];
                        board[k][j] = 0;
                    } else if (board[k + 1][j] == board[k][j]) {
                        board[k + 1][j] *= 2;
                        board[k][j] = 0;
                        if (board[k + 1][j] == 2048) {
                            win = true;
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        addTile();
    }

    private static void moveLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                for (int k = j; k > 0; k--) {
                    if (board[i][k - 1] == 0) {
                        board[i][k - 1] = board[i][k];
                        board[i][k] = 0;
                    } else if (board[i][k - 1] == board[i][k]) {
                        board[i][k - 1] *= 2;
                        board[i][k] = 0;
                        if (board[i][k - 1] == 2048) {
                            win = true;
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        addTile();
    }

    private static void moveRight() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 2; j >= 0; j--) {
                for (int k = j; k < SIZE - 1; k++) {
                    if (board[i][k + 1] == 0) {
                        board[i][k + 1] = board[i][k];
                        board[i][k] = 0;
                    } else if (board[i][k + 1] == board[i][k]) {
                        board[i][k + 1] *= 2;
                        board[i][k] = 0;
                        if (board[i][k + 1] == 2048) {
                            win = true;
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        addTile();
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
}
