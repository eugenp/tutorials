package com.bealdung.graphqlDGS;

public class Album {
    private final String title;
    private final String artist;
    private final Integer recordNo;

    public Album(String title, String artist, Integer recordNo) {
        this.title = title;
        this.recordNo = recordNo;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getRecordNo() {
        return recordNo;
    }
}
