import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private final InputButton[][] iButtons = new InputButton[3][3];
    private JButton resetScore;
    private JButton symbol;
    private String currentXSymbol = "X";
    private String currentOSymbol = "O";
    private String currentPlayer;
    private JLabel scoreLabel;
    private int playerXScore;
    private int playerOScore;
    private final Font scoreFont;
    private final String[][] symbolList = {
            {"X", "O"}, {"$", "€"}, {"0", "1"}, {"!", "?"}
    };
    private int symbolN = 0;

    public Main() {
        setTitle("TIC-TAC-TOE");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentPlayer = currentXSymbol;
        playerXScore = 0;
        playerOScore = 0;
        scoreFont = new Font("Arial", Font.BOLD, 15);
        //gameFont = new Font(Font.SANS_SERIF, Font.BOLD, 52);

        initializeBoard();
        initializeScoreLabel();
        initializeOptionPanel();
    }

    private void initializeBoard() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                iButtons[row][col] = new InputButton();

                int finalRow = row;
                int finalCol = col;

                iButtons[row][col].getInputButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(finalRow, finalCol);
                    }
                });

                buttonPanel.add(iButtons[row][col].getInputButton());
            }
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void initializeScoreLabel() {
        scoreLabel = new JLabel("SCORE (Player " + currentXSymbol + ":  " + playerXScore + " /  Player " + currentOSymbol + ":  " + playerOScore + ")", SwingConstants.CENTER);
        scoreLabel.setPreferredSize(new Dimension(100, 35));
        scoreLabel.setFont(scoreFont);
        add(scoreLabel, BorderLayout.NORTH);
    }

    private void initializeOptionPanel() {
        JPanel optionsPanel = new JPanel(new GridLayout(1, 2));
        add(optionsPanel, BorderLayout.SOUTH);
        initializeResetButton();
        initializeSymbolButton();
        optionsPanel.add(symbol, 0);
        optionsPanel.add(resetScore, 1);
    }
    private void initializeResetButton() {
        resetScore = new JButton("Reset");
        resetScore.setFont(scoreFont);
        resetScore.setPreferredSize(new Dimension(100, 50));
        resetScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonClick();
            }
        });
    }

    private void initializeSymbolButton() {
        symbol = new JButton("Symbol: " + currentXSymbol + ", " + currentOSymbol);
        //add(symbol, BorderLayout.SOUTH);
        symbol.setFont(scoreFont);
        symbol.setPreferredSize(new Dimension(100, 50));
        symbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                symbolButtonClick();
            }
        });
    }

    private void updateScoreLabel() {
        scoreLabel.setText("SCORE (Player " + currentXSymbol + ":  " + playerXScore + " /  Player " + currentOSymbol + ":  " + playerOScore + ")");
    }

    private void handleButtonClick(int row, int col) {
        if (iButtons[row][col].getButtonText().isEmpty()) {
            if(currentPlayer.equals(currentXSymbol)) iButtons[row][col].getInputButton().setForeground(Color.BLUE);
            else iButtons[row][col].getInputButton().setForeground(Color.RED);
            iButtons[row][col].setButtonText(currentPlayer);

            if (checkForWin(row, col)) {
                announceWinner();
                updateScoreLabel();
            } else if (isBoardFull()) {
                announceDraw();
                updateScoreLabel();
            } else {
                switchPlayer();
            }
        }
    }

    private void resetButtonClick(){
        playerOScore = 0;
        playerXScore = 0;
        currentPlayer = currentXSymbol;
        resetGame();
        updateScoreLabel();
    }

    private void symbolButtonClick() {
        symbolN++;
        if (symbolN > symbolList.length - 1) symbolN = 0;
        currentXSymbol = symbolList[symbolN][0];
        currentOSymbol = symbolList[symbolN][1];
        updateScoreLabel();
        updateSymbolButton();
        resetGame();
        currentPlayer = currentXSymbol;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(currentXSymbol)) ? currentOSymbol : currentXSymbol;
    }

    private boolean checkForWin(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonals(row, col);
    }

    private boolean checkRow(int row) {
        return iButtons[row][0].getButtonText().equals(currentPlayer) &&
                iButtons[row][1].getButtonText().equals(currentPlayer) &&
                iButtons[row][2].getButtonText().equals(currentPlayer);
    }

    private boolean checkColumn(int col) {
        return iButtons[0][col].getButtonText().equals(currentPlayer) &&
                iButtons[1][col].getButtonText().equals(currentPlayer) &&
                iButtons[2][col].getButtonText().equals(currentPlayer);
    }

    private boolean checkDiagonals(int row, int col) {
        return checkMainDiagonal(row, col) || checkAntiDiagonal(row, col);
    }

    private boolean checkMainDiagonal(int row, int col) {
        return row == col &&
                iButtons[0][0].getButtonText().equals(currentPlayer) &&
                iButtons[1][1].getButtonText().equals(currentPlayer) &&
                iButtons[2][2].getButtonText().equals(currentPlayer);
    }

    private boolean checkAntiDiagonal(int row, int col) {
        return row + col == 2 &&
                iButtons[0][2].getButtonText().equals(currentPlayer) &&
                iButtons[1][1].getButtonText().equals(currentPlayer) &&
                iButtons[2][0].getButtonText().equals(currentPlayer);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
//              if (buttons[row][col].getText().isEmpty()) {
                if (iButtons[row][col].getButtonText().isEmpty()) {
                        return false;
                }
            }
        }
        return true;
    }

    private void announceWinner() {
        JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
        updateScore();
        resetGame();
    }

    private void announceDraw() {
        JOptionPane.showMessageDialog(this, "It's a draw!");
        resetGame();
    }

    private void updateScore() {
        if (currentPlayer.equals(currentXSymbol)) {
            playerXScore++;
        } else {
            playerOScore++;
        }
    }

    private void updateSymbolButton() {
        symbol.setText("Symbol: " + currentXSymbol + ", " + currentOSymbol);
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                iButtons[row][col].setButtonText("");
            }
        }
        //currentPlayer = "X";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}
