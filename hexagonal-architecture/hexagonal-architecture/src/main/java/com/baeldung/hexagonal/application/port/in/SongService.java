package com.baeldung.hexagonal.application.port.in;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.application.domain.Song;
import com.baeldung.hexagonal.application.port.out.SongPersistencePort;

@Service
public class SongService implements SongUseCase {

	
	private final SongPersistencePort songPersistencePort;

	@Autowired
	public SongService(SongPersistencePort songPersistencePort) {
		this.songPersistencePort = songPersistencePort;
	}

	@Override
	public Integer addSong(Song song) {
		return songPersistencePort.addSong(song);
	}

	@Override
	public void removeSong(Song song) {
		songPersistencePort.removeSong(song);
	}

	@Override
	public List<Song> getSongs() {
		return songPersistencePort.getSongs();
	}

	@Override
	public Song getSongById(Integer songId) {
		return songPersistencePort.getSongById(songId);
	}
}
