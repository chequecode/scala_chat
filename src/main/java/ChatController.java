import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChatController {
    @FXML
    private TextArea txtAreaDisplay;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnSend;

    private Stage stage;

    public void initialize(Stage stage) {
        this.stage = stage;
        Main.chatArea = txtAreaDisplay;
    }

    @FXML
    private void handleSendMessage() {
        String message = txtInput.getText();

        if (message.isEmpty() || Main.userName == null) {
            return;
        }
        if (message.startsWith("PM")) {
            String[] a = message.split(": ");
            if (a.length == 2) {
                String namePM = a[1];
                if (Main.members.containsKey(namePM)) {
                    Main.members.get(namePM).tell(new User.JoinPrivateChat(Main.userName));
                    Main.system.tell(new User.JoinPrivateChat(namePM));
                }
            }
        } else {
            Main.system.tell(new User.Message(message, Main.userName));
        }
        txtInput.clear();
    }
}