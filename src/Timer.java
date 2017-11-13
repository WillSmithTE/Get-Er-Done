import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by elbershayz on 13/11/17.
 */
public class Timer {
    private int time;
    private String tune;
    private List<String> tunes = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public Timer(){
        this.tunes.add("none");
        this.tunes.add("Macaroni");
    }

    public void waitTime() {
        this.time = getTime();
        this.tune = getTune();
        int i = 1;
        while(i<=time)
        {
            System.out.println("CURRENT MODE IS MUTE");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        if (!tune.equals("none")) {
            try {
                playTune(tune);
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
    }

    private String getTune() {
        tunes.forEach(tune -> System.out.println(tunes.indexOf(tune) + 1 + " - " + tune));
        System.out.print("Tune choice: ");
        return tunes.get(scanner.nextInt()-1);
    }

    protected int getTime() {
        System.out.print("Wait time: ");
        return scanner.nextInt();
    }

    private void playTune(String tune) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException,
            InterruptedException{
        class AudioListener implements LineListener {
            private boolean done = false;

            @Override
            public synchronized void update(LineEvent event) {
                LineEvent.Type eventType = event.getType();
                if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
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
                    Main.class.getResourceAsStream(tune + ".wav"));
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
