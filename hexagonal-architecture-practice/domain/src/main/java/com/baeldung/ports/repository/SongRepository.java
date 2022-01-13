package com.baeldung.ports.repository;

import com.baeldung.data.Song;

public interface SongRepository {
    Song save(Song song);
}
