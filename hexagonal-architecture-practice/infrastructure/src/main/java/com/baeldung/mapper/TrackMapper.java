package com.baeldung.mapper;

import com.baeldung.data.Song;
import com.baeldung.entity.Track;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrackMapper {
    static ObjectMapper mapper;

    private TrackMapper() {
        // Do nothing
    }

    static {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Song mapTrackToSong(Track source) {
        return mapper.convertValue(source, Song.class);
    }

    public static Track mapSongToTrack(Song source) {
        return mapper.convertValue(source, Track.class);
    }
}
