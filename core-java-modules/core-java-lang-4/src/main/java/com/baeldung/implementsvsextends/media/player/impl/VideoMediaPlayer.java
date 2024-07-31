package com.baeldung.implementsvsextends.media.player.impl;

import com.baeldung.implementsvsextends.media.player.MediaPlayer;

public class VideoMediaPlayer implements MediaPlayer {

    @Override
    public void play() {
        System.out.println("VideoMediaPlayer is Playing");
    }

    @Override
    public void pause() {
        System.out.println("VideoMediaPlayer is Paused");
    }
}