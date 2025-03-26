import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class DirectController {
    @FXML
    private TextArea txtAreaDisplay;
    @FXML
    private TextField txtInput;

    private Stage stage;
    private String recipient;

    public static void startPrivateChat(Stage stage, String recipient) {
        try {
            FXMLLoader loader = new FXMLLoader(DirectController.class.getResource("direct_panel.fxml"));
            Parent root = loader.load();

            DirectController controller = loader.getController();
            controller.initialize(stage, recipient);

            stage.setScene(new Scene(root));
            stage.setTitle("Private Chat with " + recipient);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load private chat window");
        }
    }

    public void initialize(Stage stage, String recipient) {
        this.stage = stage;
        this.recipient = recipient;
        Main.directArea.put(recipient, txtAreaDisplay);

        txtInput.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleSendMessage();
            event.consume();
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = txtInput.getText();

        if (message.isEmpty()) {
            return;
        }
        try {
            Main.members.get(recipient).tell(new User.PrivateMessage(message, Main.userName, Main.userName));
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            System.out.println("Chat not found");
        }
        Main.system.tell(new User.PrivateMessage(message, recipient, Main.userName));

        txtInput.clear();
    }
}