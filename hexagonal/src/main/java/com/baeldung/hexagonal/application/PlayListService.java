package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.core.PlayList;
import com.baeldung.hexagonal.core.Song;
import com.baeldung.hexagonal.framework.PlayListRepoAdapter;

public class PlayListService implements PlayListServicePort {

    PlayListRepoAdapter repoAdapter;

    public PlayListService(PlayListRepoAdapter repoAdapter) {
        this.repoAdapter = repoAdapter;
    }

    @Override
    public PlayList getPlayList(Long id) {
        return repoAdapter.getPlayList(id);
    }

    @Override
    public Song getSong(Long id, String artist, String name) {
        return repoAdapter.getPlayList(id)
            .getSong(artist, name);
    }

    @Override
    public void addSong(Long id, Song song) {
        repoAdapter.getPlayList(id)
            .addSong(song);
    }

    @Override
    public void removeSong(Long id, Song song) {
        repoAdapter.getPlayList(id)
            .removeSong(song);
    }
}
