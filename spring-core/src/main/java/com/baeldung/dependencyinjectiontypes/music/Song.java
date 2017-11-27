package com.baeldung.dependencyinjectiontypes.music;

public class Song {
    private String lyrics;

    public Song() {
        lyrics = "Nanana, lalala ...";
    }

    public String getLyrics() {
        return lyrics;
    }

}
