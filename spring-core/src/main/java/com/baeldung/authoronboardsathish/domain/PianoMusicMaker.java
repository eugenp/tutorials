package com.baeldung.authoronboardsathish.domain;

import org.springframework.stereotype.Component;

@Component
public class PianoMusicMaker {
    private String nameOfMusic;

    public String getNameOfMusic() {
        return nameOfMusic;
    }

    public void setNameOfMusic(String nameOfMusic) {
        this.nameOfMusic = nameOfMusic;
    }

    @Override
    public String toString() {
        return "PianoMusicMaker{" + "nameOfMusic='" + nameOfMusic + '\'' + '}';
    }
}
