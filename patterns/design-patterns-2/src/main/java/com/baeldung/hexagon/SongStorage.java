package com.baeldung.hexagon;

public interface SongStorage {
    public Song findSong(String title);
    public void saveSong(String title, String artist);
}
