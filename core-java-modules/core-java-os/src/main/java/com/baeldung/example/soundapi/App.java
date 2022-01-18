package com.baeldung.example.soundapi;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class App {
    public static void main(String[] args) throws Exception {

        AudioFormat format = buildAudioFormatInstance();

        SoundRecorder soundRecorder = new SoundRecorder();
        soundRecorder.build(format);

        System.out.println("Start recording ....");
        soundRecorder.start();
        Thread.sleep(20000);
        soundRecorder.stop();

        WaveDataUtil wd = new WaveDataUtil();
        Thread.sleep(3000);
        wd.saveToFile("/SoundClip", AudioFileFormat.Type.WAVE, soundRecorder.getAudioInputStream());
    }

    public static AudioFormat buildAudioFormatInstance() {
        ApplicationProperties aConstants = new ApplicationProperties();
        AudioFormat.Encoding encoding = aConstants.ENCODING;
        float rate = aConstants.RATE;
        int channels = aConstants.CHANNELS;
        int sampleSize = aConstants.SAMPLE_SIZE;
        boolean bigEndian = aConstants.BIG_ENDIAN;

        return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
    }
}