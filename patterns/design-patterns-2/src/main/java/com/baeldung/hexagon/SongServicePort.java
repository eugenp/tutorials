package com.baeldung.hexagon;

public interface SongServicePort {
    public Song findSong(String title);
    public void addSong(String title, String artist);
}
