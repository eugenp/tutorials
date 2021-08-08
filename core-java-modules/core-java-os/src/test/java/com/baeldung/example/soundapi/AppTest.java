package com.baeldung.example.soundapi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import javax.sound.sampled.AudioFormat;

public class AppTest {
    @Test
    public void when_run_save_SoundClip() {

        try {

            AudioFormat af = App.buildAudioFormatInstance();
            SoundRecorder soundRecorder = new SoundRecorder();
            soundRecorder.build(af);

            soundRecorder.start();
            soundRecorder.stop();

        } catch (Exception ex) {
            fail("Exception: " + ex);
        }

    }
}
