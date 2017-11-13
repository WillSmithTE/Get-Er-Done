import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.File;
import javax.sound.sampled.LineEvent.Type;
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

    private static void waitTime(long time) {
            int i = 1;
        while(i<=time)
            {
                System.out.println("CURRENT MODE IS MUTE");
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        try {
            playTune("Macaroni");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected static long getTime() {
            System.out.print("Wait time: ");
            return new Scanner(System.in).nextLong();
        }

    private static void playTune(String tune) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException,
            InterruptedException{
        class AudioListener implements LineListener {
            private boolean done = false;

            @Override
            public synchronized void update(LineEvent event) {
                Type eventType = event.getType();
                if (eventType == Type.STOP || eventType == Type.CLOSE) {
                    done = true;
                    notifyAll();
                }
            }

            public synchronized void waitUntilDone() throws InterruptedException {
                while (!done) {
                    wait();
                }
            }
        }
            AudioListener listener = new AudioListener();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream(tune+".wav"));
            try{
                Clip clip = AudioSystem.getClip();
                clip.addLineListener(listener);
                clip.open(audioInputStream);
                try {
                    clip.start();
                    listener.waitUntilDone();
                } finally {
                    clip.close();
                }
            } finally {
                audioInputStream.close();
            }
        }


}
