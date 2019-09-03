package com.baeldung.hexagon;

public interface SongManagerPort {
    public Song findSong(String title);
    public void saveSong(String title, String artist);
}
