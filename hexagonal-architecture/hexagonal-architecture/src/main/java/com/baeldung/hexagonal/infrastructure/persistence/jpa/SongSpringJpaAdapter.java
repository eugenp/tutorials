	package com.baeldung.hexagonal.infrastructure.persistence.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.baeldung.hexagonal.application.domain.Song;
import com.baeldung.hexagonal.application.port.out.SongPersistencePort;

import lombok.RequiredArgsConstructor;

//@Component
@Profile("jpa")
@RequiredArgsConstructor
public class SongSpringJpaAdapter implements SongPersistencePort {

	 private final SongRepository songRepository;
	 

	@Override
	public Integer addSong(Song song) {
		SongEntity songEntity = new SongEntity();
        BeanUtils.copyProperties(song, songEntity);
        songEntity = songRepository.save(songEntity);
        return songEntity.getSongId();
	}

	@Override
	public void removeSong(Song song) {
		SongEntity songEntity = new SongEntity();
        BeanUtils.copyProperties(song, songEntity);
        songRepository.delete(songEntity);
	}

	@Override
	public void removeSongs() {
		songRepository.deleteAll();
	}

	
	@Override
	public List<Song> getSongs() {
		List<Song> songList = new ArrayList<Song>();
        List<SongEntity> songEntityList = songRepository.findAll();
        for(SongEntity songEntity : songEntityList) {
            Song song = new Song();
            BeanUtils.copyProperties(songEntity, song);
            songList.add(song);
        }
        return songList;
	}

	@Override
	public Song getSongById(Integer songId) {
		SongEntity songEntity = songRepository.findBySongId(songId);
        Assert.notNull(songEntity, String.format("Song with provided id=%d was not found.",songId));
		
        Song song = new Song();
        BeanUtils.copyProperties(songEntity, song);

        return song;
	}

}

