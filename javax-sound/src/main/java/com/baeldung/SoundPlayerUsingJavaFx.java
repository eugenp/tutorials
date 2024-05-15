package com.baeldung;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayerUsingJavaFx {

    public static void main(String[] args) {

        // String audioFilePath = "AudioFileWithWavFormat.wav";
        // String audioFilePath = "AudioFileWithMpegFormat.mpeg";
        String audioFilePath = "AudioFileWithMp3Format.mp3";
        SoundPlayerUsingJavaFx soundPlayerWithJavaFx = new SoundPlayerUsingJavaFx();

        try {
            com.sun.javafx.application.PlatformImpl.startup(() -> {
            });

            Media media = new Media(soundPlayerWithJavaFx.getClass()
                .getClassLoader()
                .getResource(audioFilePath)
                .toExternalForm());

            MediaPlayer mp3Player = new MediaPlayer(media);
            mp3Player.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Playback started");
                }
            });

            mp3Player.play();

        } catch (Exception ex) {
            System.out.println("Error occured during playback process:" + ex.getMessage());
        }

    }
}
