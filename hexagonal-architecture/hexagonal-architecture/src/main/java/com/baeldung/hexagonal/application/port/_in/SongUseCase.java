package com.baeldung.hexagonal.application.port._in;

import java.util.List;

import com.baeldung.hexagonal.application.domain.Song;

public interface SongUseCase {

	Integer addSong(Song song);

	void removeSong(Song song);

	List<Song> getSongs();

	Song getSongById(Integer songId);

}
