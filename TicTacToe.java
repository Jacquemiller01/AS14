import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private static final int BOARD_SIZE = 5;
    private static final int WIN_LENGTH = 5;
    private static final String PLAYER_X_MARK = "X";
    private static final String PLAYER_O_MARK = "O";

    private String currentPlayer;
    private Button[][] boardButtons;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Toe");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        boardButtons = new Button[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = new Button("");
                button.setPrefSize(50, 50);
                button.setOnAction(event -> {
                    playMove(button);
                });
                boardButtons[i][j] = button;
                grid.add(button, j, i);
            }
        }

        currentPlayer = PLAYER_X_MARK;

        Scene scene = new Scene(grid, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playMove(Button button) {
        if (button.getText().isEmpty()) {
            button.setText(currentPlayer);
            if (checkWin()) {
                showWin(currentPlayer);
            } else if (checkDraw()) {
                showDraw();
            } else {
                currentPlayer = currentPlayer.equals(PLAYER_X_MARK) ? PLAYER_O_MARK : PLAYER_X_MARK;
            }
        }
    }

    private boolean checkWin() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            int count = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (boardButtons[i][j].getText().equals(currentPlayer)) {
                    count++;
                    if (count == WIN_LENGTH) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            int count = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (boardButtons[i][j].getText().equals(currentPlayer)) {
                    count++;
                    if (count == WIN_LENGTH) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        for (int i = 0; i <= BOARD_SIZE - WIN_LENGTH; i++) {
            for (int j = 0; j <= BOARD_SIZE - WIN_LENGTH; j++) {
                boolean leftToRight = true;
                boolean rightToLeft = true;
                for (int k = 0; k < WIN_LENGTH; k++) {
                    if (!boardButtons[i+k][j+k].getText().equals(currentPlayer)) {
                        leftToRight = false;
                    }
                    if (!boardButtons[i+k][j+WIN_LENGTH-1-k].getText().equals(currentPlayer)) {
                        rightToLeft = false;
                    }
                }
                if (leftToRight || rightToLeft) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (boardButtons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWin(String player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Player " + player + " wins!");
        alert.showAndWait();
        resetGame();
    }

    private void showDraw() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Draw!");
        alert.showAndWait();
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardButtons[i][j].setText("");
            }
        }
        currentPlayer = PLAYER_X_MARK;
    }

    public static void main(String[] args) {
        launch(args);
    }
}