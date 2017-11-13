import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Timer timer = new Timer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Timers.fxml"));
        primaryStage.setTitle("Time Up");
        primaryStage.setScene(new Scene(root, 300, 275));
        timer.waitTime();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }




}
