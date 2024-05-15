package com.baeldung;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayerUsingClip implements LineListener {

    boolean isPlaybackCompleted;

    @Override
    public void update(LineEvent event) {

        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }

    /**
    * 
    * Play a given audio file.
    * @param audioFilePath Path of the audio file.
    * 
    */
    void play(String audioFilePath) {
        try {
            InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();
            while (!isPlaybackCompleted) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            audioClip.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.out.println("Error occured during playback process:"+ ex.getMessage());
        }

    }

    public static void main(String[] args) {
        String audioFilePath = "AudioFileWithWavFormat.wav";

        // Clip can not play mpeg/mp3 format audio. We'll get exception if we run with below commented mp3 and mpeg format audio.
        // String audioFilePath = "AudioFileWithMpegFormat.mpeg";
        // String audioFilePath = "AudioFileWithMp3Format.mp3";

        SoundPlayerUsingClip player = new SoundPlayerUsingClip();
        player.play(audioFilePath);
    }

}