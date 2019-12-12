package com.baeldung.hexagonal.architecture.domain.port;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;

public interface ArtistPersistencePort {

    ArtistDto getArtistById(Long artistId);
}
