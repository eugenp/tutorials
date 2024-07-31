package com.baeldung;

import java.io.BufferedInputStream;

import javazoom.jl.player.Player;

public class SoundPlayerUsingJavaZoom {

    public static void main(String[] args) {

        // javazoom Player doesn't work with wav audio format.
        // String audioFilePath = "AudioFileWithWavFormat.wav";

        // It works with below audio formats.
        // String audioFilePath = "AudioFileWithMpegFormat.mpeg";
        String audioFilePath = "AudioFileWithMp3Format.mp3";
        SoundPlayerUsingJavaZoom player = new SoundPlayerUsingJavaZoom();

        try {
            BufferedInputStream buffer = new BufferedInputStream(player.getClass()
                .getClassLoader()
                .getResourceAsStream(audioFilePath));
            Player mp3Player = new Player(buffer);
            mp3Player.play();

        } catch (Exception ex) {
            System.out.println("Error occured during playback process:" + ex.getMessage());
        }
    }
}
