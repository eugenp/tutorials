package com.baeldung.implementsvsextends.media.player.impl;

import com.baeldung.implementsvsextends.media.model.Media;
import com.baeldung.implementsvsextends.media.player.MediaPlayer;

public class CustomMediaPlayer extends Media implements MediaPlayer {

    @Override
    public void play() {
        System.out.println("CustomMediaPlayer is Playing");
    }

    @Override
    public void pause() {
        System.out.println("CustomMediaPlayer is Paused");
    }
}