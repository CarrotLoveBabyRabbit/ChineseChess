package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class AudioPlayer {
        public void audioPlayer(String str) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(str));
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);

                SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();

                int count;
                byte[] tempBuffer = new byte[1024];
                while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                    if (count > 0) {
                        sourceDataLine.write(tempBuffer, 0, count);
                    }
                }
                sourceDataLine.drain();
                sourceDataLine.close();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }

        }

        public void playAudio(String str){
            Thread playAudio;
            playAudio = new Thread(() -> audioPlayer(str));
            playAudio.start();
        }
}
