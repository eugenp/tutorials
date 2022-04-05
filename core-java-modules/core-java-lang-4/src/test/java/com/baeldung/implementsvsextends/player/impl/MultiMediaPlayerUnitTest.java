package com.baeldung.implementsvsextends.media.player.impl;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MultiMediaPlayerUnitTest {

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
    public void givenMultiMediaPlayer_whenPlay_thenPrintsPlayingString() {
        MultiMediaPlayer multiMediaPlayer = new MultiMediaPlayer();
        multiMediaPlayer.play();
        Assert.assertEquals("MultiMediaPlayer is Playing", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void givenMultiMediaPlayer_whenPause_thenPrintsPausedString() {
        MultiMediaPlayer multiMediaPlayer = new MultiMediaPlayer();
        multiMediaPlayer.pause();
        Assert.assertEquals("MultiMediaPlayer is Paused", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void givenMultiMediaPlayer_whenSeek_thenPrintsPausedString() {
        MultiMediaPlayer multiMediaPlayer = new MultiMediaPlayer();
        multiMediaPlayer.seek();
        Assert.assertEquals("MultiMediaPlayer is being seeked", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void givenMultiMediaPlayer_whenFastForward_thenPrintsPausedString() {
        MultiMediaPlayer multiMediaPlayer = new MultiMediaPlayer();
        multiMediaPlayer.fastForward();
        Assert.assertEquals("MultiMediaPlayer is being fast forwarded",
                outputStreamCaptor.toString()
                .trim());
    }
}