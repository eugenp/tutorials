package com.baeldung.example.soundapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

public class AppTest {

    AudioFormat af = App.buildAudioFormatInstance();
    SoundRecorder soundRecorder = new SoundRecorder();

    @Test
    public void when_run_save_SoundClip() {

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
    public void when_run_get_targetdataline() {
        soundRecorder.setFormat(af);
        Assertions.assertDoesNotThrow(() -> soundRecorder.getTargetDataLineForRecord());
    }

    @Test
    public void when_run_build_byte_ouptstream() {

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
    public void when_run_then_save_file() {
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
