package com.baeldung.implementsvsextends.media.player.impl;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class VideoMediaPlayerUnitTest {

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
    public void givenVideoMediaPlayer_whenPlay_thenPrintsPlayingString() {
        VideoMediaPlayer videoMediaPlayer = new VideoMediaPlayer();
        videoMediaPlayer.play();
        Assert.assertEquals("VideoMediaPlayer is Playing", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void givenVideoMediaPlayer_whenPause_thenPrintsPausedString() {
        VideoMediaPlayer videoMediaPlayer = new VideoMediaPlayer();
        videoMediaPlayer.pause();
        Assert.assertEquals("VideoMediaPlayer is Paused", outputStreamCaptor.toString()
                .trim());
    }
}