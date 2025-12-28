package io.github.lucasynoguti.util;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoundPlayer {
    // cache to store sounds in ram memory
    // concurrent to guarantee thread safety
    private static final Map<String, byte[]> soundCache = new ConcurrentHashMap<>();

    public static void loadSounds(String... paths) {
        for (String path : paths) {
            try {
                getAudioData(path);
                warmUpAudioSystem(); // loads a silent clip to warm up the audio system
                System.out.println("Loaded sound: " + path);
            } catch (IOException e) {
                System.err.println("Error while loading sound: " + path);
            }
        }
    }

    private static void warmUpAudioSystem() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(new byte[100])));
            clip.close();
        } catch (Exception ignored) {}
    }


    public static void playSound(String resourcePath) {
        // playing sound in thread to avoid blocking the UI
        new Thread(() -> {
            try {
                byte[] audioData = getAudioData(resourcePath);
                if (audioData == null) return;

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioData));
                Clip clip = AudioSystem.getClip();

                clip.open(audioStream);

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private static byte[] getAudioData(String resourcePath) throws IOException {
        if (soundCache.containsKey(resourcePath)) {
            return soundCache.get(resourcePath);
        }
        try (InputStream is = SoundPlayer.class.getResourceAsStream(resourcePath)) {
            if (is == null) return null;
            byte[] data = is.readAllBytes();
            soundCache.put(resourcePath, data);
            return data;
        }
    }
}