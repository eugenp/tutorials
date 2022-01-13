package com.baeldung.service;

import com.baeldung.data.Song;
import com.baeldung.ports.api.SongService;
import com.baeldung.ports.repository.SongRepository;

public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }
}
