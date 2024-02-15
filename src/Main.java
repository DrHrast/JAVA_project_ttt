import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JButton[][] buttons;
    private JButton resetScore;
    private JButton symbol;
    private String currentXSymbol = "X";
    private String currentOSymbol = "O";
    private String currentPlayer;
    private JLabel scoreLabel;
    private int playerXScore;
    private int playerOScore;
    private Font scoreFont;
    private Font gameFont;
    private String[][] symbolList = {
            {"X", "O"}, {"$", "€"}, {"0", "1"}, {"!", "?"}
    };
    private int symboln = 0;

    public Main() {
        setTitle("TIC-TAC-TOE");
        setSize(350, 400); // Increased height to accommodate the score label
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttons = new JButton[3][3];
        currentPlayer = currentXSymbol;
        playerXScore = 0;
        playerOScore = 0;
        scoreFont = new Font("Arial", Font.BOLD, 15);
        gameFont = new Font(Font.SANS_SERIF, Font.BOLD, 52);

        initializeBoard();
        initializeScoreLabel();
        //initializeResetButton();
        //initializeSymbolButton();
        initializeOptionPanel();
    }

    private void initializeBoard() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(gameFont);
                buttons[row][col].setFocusPainted(false);

                int finalRow = row;
                int finalCol = col;

                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(finalRow, finalCol);
                    }
                });

                buttonPanel.add(buttons[row][col]);
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
        if (buttons[row][col].getText().isEmpty()) {
            if(currentPlayer.equals(currentXSymbol)) buttons[row][col].setForeground(Color.BLUE);
            else buttons[row][col].setForeground(Color.RED);
            buttons[row][col].setText(currentPlayer);

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
        symboln++;
        if (symboln > symbolList.length - 1) symboln = 0;
        currentXSymbol = symbolList[symboln][0];
        currentOSymbol = symbolList[symboln][1];
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
        return buttons[row][0].getText().equals(currentPlayer) &&
                buttons[row][1].getText().equals(currentPlayer) &&
                buttons[row][2].getText().equals(currentPlayer);
    }

    private boolean checkColumn(int col) {
        return buttons[0][col].getText().equals(currentPlayer) &&
                buttons[1][col].getText().equals(currentPlayer) &&
                buttons[2][col].getText().equals(currentPlayer);
    }

    private boolean checkDiagonals(int row, int col) {
        return checkMainDiagonal(row, col) || checkAntiDiagonal(row, col);
    }

    private boolean checkMainDiagonal(int row, int col) {
        return row == col &&
                buttons[0][0].getText().equals(currentPlayer) &&
                buttons[1][1].getText().equals(currentPlayer) &&
                buttons[2][2].getText().equals(currentPlayer);
    }

    private boolean checkAntiDiagonal(int row, int col) {
        return row + col == 2 &&
                buttons[0][2].getText().equals(currentPlayer) &&
                buttons[1][1].getText().equals(currentPlayer) &&
                buttons[2][0].getText().equals(currentPlayer);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
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
                buttons[row][col].setText("");
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
