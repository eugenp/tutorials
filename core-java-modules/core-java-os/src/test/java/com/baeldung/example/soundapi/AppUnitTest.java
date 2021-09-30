package com.baeldung.example.soundapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

public class AppUnitTest {

    AudioFormat af = App.buildAudioFormatInstance();
    SoundRecorder soundRecorder = new SoundRecorder();

    @Test
    public void Given_SoundRecorderObject_When_Run_Then_ThrowsNoException() {

        soundRecorder.build(af);
        try {
            soundRecorder.start();
            Thread.sleep(20000);
            soundRecorder.stop();
        } catch (InterruptedException ex) {
            fail("Exception: " + ex);
        }

    }

    @Test
    public void Given_AudioFormatObject_When_NotNull_Then_ReturnsTargetDataLine() {
        soundRecorder.setFormat(af);
        Assertions.assertDoesNotThrow(() -> soundRecorder.getTargetDataLineForRecord());
    }

    @Test
    public void Given_TargetLineDataObject_When_Run_Then_GeneratesOutputStream() {

        soundRecorder.setFormat(af);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TargetDataLine tLine = soundRecorder.getTargetDataLineForRecord();

        int frameSizeInBytes = af.getFrameSize();
        int bufferLengthInFrames = tLine.getBufferSize() / 8;
        final int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;

        Assertions.assertDoesNotThrow(
                () -> soundRecorder.buildByteOutputStream(out, tLine, frameSizeInBytes, bufferLengthInBytes));
    }

    @Test
    public void Given_AudioInputStream_When_NotNull_Then_SaveToWavFile() {
        soundRecorder.setFormat(af);
        soundRecorder.build(af);
        try {
            soundRecorder.start();
            Thread.sleep(20000);
            soundRecorder.stop();
            WaveDataUtil wd = new WaveDataUtil();
            Thread.sleep(3000);
            boolean saveFile = wd.saveToFile("/SoundClip", AudioFileFormat.Type.WAVE,
                    soundRecorder.getAudioInputStream());

            assertEquals(saveFile, true);

        } catch (InterruptedException ex) {
            fail("Exception: " + ex);
        }

    }

}
