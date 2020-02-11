package com.baeldung.hexagonal.application.port._out;

import java.util.List;

import com.baeldung.hexagonal.application.domain.Song;

public interface SongPersistencePort {

	Integer addSong(Song song); 

    void removeSong(Song song);
    
    void removeSongs();

    List<Song> getSongs();

    Song getSongById(Integer songId); 
}
