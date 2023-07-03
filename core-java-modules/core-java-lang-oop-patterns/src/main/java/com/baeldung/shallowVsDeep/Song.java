package com.baeldung.shallowVsDeep;

public class Song {
    private String songName;
    private String artistName;

    public Song(String songName, String artistName) {
        this.songName = songName;
        this.artistName = artistName;
    }

    // Deep Copy Constructor
    public Song(Song song) {
        this.songName = song.getSongName();
        this.artistName = song.getArtistName();
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Song{" + "songName='" + songName + '\'' + ", artistName='" + artistName + '\'' + '}';
    }
}
