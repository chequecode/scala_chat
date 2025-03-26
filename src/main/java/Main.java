import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    static Map<String, ActorRef<User.Command>> members = new HashMap<>();
    static Map<String, TextArea> directArea = new HashMap<>();
    static TextArea chatArea;
    static ActorSystem<User.Command> system;
    static String userName;
    static int seedPort1;
    static int seedPort2;

    public static void setSystem(ActorSystem<User.Command> system) {
        Main.system = system;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader chatLoader = new FXMLLoader(Main.class.getResource("chat_panel.fxml"));
            Parent chatRoot = chatLoader.load();

            FXMLLoader authLoader = new FXMLLoader(Main.class.getResource("auth_panel.fxml"));
            Parent authRoot = authLoader.load();

            Stage chatStage = new Stage();
            chatStage.setScene(new Scene(chatRoot, 450, 500));
            chatStage.setTitle("Chat");

            Stage authStage = new Stage();
            authStage.setScene(new Scene(authRoot, 300, 275));
            authStage.setTitle("Auth");

            ChatController chatController = chatLoader.getController();
            chatController.initialize(chatStage);

            AuthController authController = authLoader.getController();
            authController.initialize(authStage, chatStage);

            chatStage.show();
            authStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String ip = null;
        int port = 0;
        if (args.length != 4)
            throw new IllegalArgumentException("Usage: ./gradlew run --args=\"127.0.0.1 25251 25252 port\"");

        try {
            ip = args[0];
            seedPort1 = Integer.parseInt(args[1]);
            seedPort2 = Integer.parseInt(args[2]);
            port = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        StartAkkaCluster.startup(ip, seedPort1, seedPort2, port);
        launch(args);
    }
}