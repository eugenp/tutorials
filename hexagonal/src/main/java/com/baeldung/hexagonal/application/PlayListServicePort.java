package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.core.PlayList;
import com.baeldung.hexagonal.core.Song;

public interface PlayListServicePort {

    public PlayList getPlayList(Long id);

    public Song getSong(Long id, String artist, String name);

    public void addSong(Long id, Song song);

    public void removeSong(Long id, Song song);
}
