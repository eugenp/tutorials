package com.baeldung.authoronboardsathish.domain;

import org.springframework.stereotype.Component;

@Component
public class ViolinMusicMaker {
    private String noteType;
    private String nameOfMusic;

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNameOfMusic() {
        return nameOfMusic;
    }

    public void setNameOfMusic(String nameOfMusic) {
        this.nameOfMusic = nameOfMusic;
    }

    @Override
    public String toString() {
        return "ViolinMusicMaker{" + "noteType='" + noteType + '\'' + ", nameOfMusic='" + nameOfMusic + '\'' + '}';
    }

}
