import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AuthController {
    @FXML private TextField userTextField;
    @FXML private Button signInButton;
    @FXML private Text actiontarget;

    private Stage authStage;
    private Stage chatStage;

    public void initialize(Stage authStage, Stage chatStage) {
        this.authStage = authStage;
        this.chatStage = chatStage;

        signInButton.setOnAction(e -> handleSignIn());
        userTextField.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleSignIn();
        }
    }

    private void handleSignIn() {
        String userName = userTextField.getText();

        if (!userName.isEmpty()) {
            if (Main.members.containsKey(userName)) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("This username already exists: " + userName);
            } else {
                Main.userName = userName;
                Main.system.tell(new User.Join(Main.userName));

                authStage.close();
                chatStage.show();
            }
        } else {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Username must not be empty");
        }
    }
}