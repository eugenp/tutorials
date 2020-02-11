package com.baeldung.hexagonal.infrastructure.web;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.application.domain.Song;
import com.baeldung.hexagonal.application.port.in.SongUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SongRestAdapter {

	private final SongUseCase songService;

	
	@GetMapping("/song")
	@ResponseStatus(OK)
	public List<Song> getSongs() {
		return songService.getSongs();
	}

	@PostMapping("/song")
	@ResponseStatus(CREATED)
	public Integer addSong(@RequestBody Song song) {
		return  songService.addSong(song);
	}

	@DeleteMapping("/song")
	@ResponseStatus(OK)
	public void removeSong(@RequestBody Song song) {
		 songService.removeSong(song);
	}

	@GetMapping("/song/{songId}")
	@ResponseStatus(OK)
	public Song getSongById(@PathVariable Integer songId) {
		return songService.getSongById(songId);
	}

}
