package com.baeldung.adapters;

import com.baeldung.data.Song;
import com.baeldung.entity.Track;
import com.baeldung.mapper.TrackMapper;
import com.baeldung.ports.repository.SongRepository;
import com.baeldung.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TrackAdapter implements SongRepository {

    @Autowired
    private TrackRepository trackRepository;

    @Override
    public Song save(Song song) {
        Track track = TrackMapper.mapSongToTrack(song);
        track = trackRepository.save(track);
        return TrackMapper.mapTrackToSong(track);
    }
}