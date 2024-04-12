package com.baeldung.implementsvsextends.media.player.impl;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AudioMediaPlayerUnitTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void givenAudioMediaPlayer_whenPlay_thenPrintsPlayingString() {
        AudioMediaPlayer audioMediaPlayer = new AudioMediaPlayer();
        audioMediaPlayer.play();
        Assert.assertEquals("AudioMediaPlayer is Playing", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void givenAudioMediaPlayer_whenPause_thenPrintsPausedString() {
        AudioMediaPlayer audioMediaPlayer = new AudioMediaPlayer();
        audioMediaPlayer.pause();
        Assert.assertEquals("AudioMediaPlayer is Paused", outputStreamCaptor.toString()
                .trim());
    }
}