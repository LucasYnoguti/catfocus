package io.github.lucasynoguti.ui;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {
    public static void playSound(String resourcePath) {
        // the new thread prevents the swing worker thread from stopping during the execution of the sound
        new Thread(() -> {
            try {
                InputStream audioSrc = SoundPlayer.class.getResourceAsStream(resourcePath);
                if (audioSrc == null) {
                    System.err.println("Arquivo de som não encontrado: " + resourcePath);
                    return;
                }
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
