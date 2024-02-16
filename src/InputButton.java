import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class InputButton {
    private JButton inputButton;
    private String buttonText = "";

    public InputButton() {
        this.inputButton = new JButton(buttonText);
        Font gameFont = new Font(Font.SANS_SERIF, Font.BOLD, 52);
        this.inputButton.setFont(gameFont);
    }

    public JButton getInputButton() {
        return inputButton;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        this.inputButton.setText(buttonText);
    }

    public void handleButtonClick(String currentPlayer, String currentXSymbol) {
        if (Objects.equals(this.buttonText, "")) {
            if (currentPlayer.equals(currentXSymbol)) inputButton.setForeground(Color.BLUE);
            else inputButton.setForeground(Color.RED);
            setButtonText(currentPlayer);
        }
    }
}
