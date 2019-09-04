package com.baeldung.hexagon;

public class SongServiceImpl implements SongServicePort {

    InMemorySongManagerAdapter songManagerAdapter;

    public SongServiceImpl(InMemorySongManagerAdapter songManagerAdapter) {
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
