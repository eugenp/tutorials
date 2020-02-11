package com.baeldung.hexagonal.infrastructure.persistence.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.application.domain.Song;
import com.baeldung.hexagonal.application.port.out.SongPersistencePort;

//@Component
//@Profile("in-memory")
public class SongInMemoryAdapter implements SongPersistencePort{
	
	private static final Map<Integer, Song> songMap = new HashMap<Integer, Song>(0);

    @Override
    public Integer addSong(Song song) {
        songMap.put(song.getSongId(), song);
        return song.getSongId();
    }

    @SuppressWarnings("unlikely-arg-type")
	@Override
    public void removeSong(Song song) {
        songMap.remove(song);
    }
    
    @Override
    public void removeSongs() {
    	songMap.clear();
    }

    @Override
    public List<Song> getSongs() {
        return new ArrayList<Song>(songMap.values());
    }

    @Override
    public Song getSongById(Integer songId) {
        return songMap.get(songId);
    }

}
