import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Timers.fxml"));
        primaryStage.setTitle("Time Up");
        primaryStage.setScene(new Scene(root, 300, 275));
        waitTime(getTime());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void waitTime(long time){
        try {
                Thread.sleep(time*60000);
        } catch (InterruptedException e){
                e.printStackTrace();
            }
    }

    protected static long getTime() {
            System.out.print("Wait time: ");
            return new Scanner(System.in).nextLong();
        }


}
