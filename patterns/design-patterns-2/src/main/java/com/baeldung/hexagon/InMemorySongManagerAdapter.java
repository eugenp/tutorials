package com.baeldung.hexagon;

import java.util.HashMap;
import java.util.Map;

public class InMemorySongManagerAdapter implements SongManagerPort {

    private Map<String, Song> songStorage;

    public InMemorySongManagerAdapter() {
        this.songStorage = new HashMap<>();
    }

    @Override
    public Song findSong(String title) {
        return songStorage.get(title);
    }

    @Override
    public void saveSong(String title, String artist) {
        songStorage.put(title, new Song(title, artist));
    }
}
