package com.baeldung.hexagon;

public class SongServiceAdapter implements SongServicePort {

    SongStorageInMemoryAdapter songManagerAdapter;

    public SongServiceAdapter(SongStorageInMemoryAdapter songManagerAdapter) {
        this.songManagerAdapter = songManagerAdapter;
    }

    @Override
    public Song findSong(String title) {
        return songManagerAdapter.findSong(title);
    }

    @Override
    public void addSong(String title, String artist) {
        songManagerAdapter.saveSong(title, artist);
    }
}
