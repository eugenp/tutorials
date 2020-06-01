package com.baeldung.hexagonal.core;

import java.util.Collection;

public class PlayList {

    private Collection<Song> songs;

    public PlayList(Collection<Song> songs) {
        this.songs = songs;
    }

    public Song getSong(String artist, String name) {
        for (Song song : this.songs) {
            if (song.getArtist()
                .equals(artist)
                && song.getName()
                    .equals(name)) {
                return song;
            }
        }
        return null;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void removeSong(Song song) {
        this.songs.removeIf(item -> item.getName()
            .equals(song.getName()));
    }

    public Collection<Song> getAllSongs() {
        return this.songs;
    }
}
