package com.baeldung.bean.types;

public class SearchSongArtist {

    public String find(String title) {
        if (title.equals("Bohemian Rhapsody"))
            return "Queen";
        else
            return "unknown";
    }
}
