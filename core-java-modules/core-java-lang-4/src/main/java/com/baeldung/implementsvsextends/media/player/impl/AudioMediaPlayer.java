package com.baeldung.implementsvsextends.media.player.impl;

import com.baeldung.implementsvsextends.media.player.MediaPlayer;

public class AudioMediaPlayer implements MediaPlayer {

    @Override
    public void play() {
        System.out.println("AudioMediaPlayer is Playing");
    }

    @Override
    public void pause() {
        System.out.println("AudioMediaPlayer is Paused");
    }
}