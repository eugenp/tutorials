package com.baeldung.hexagonal.architecture.core.port;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;

public interface ArtistServicePort {

    ArtistDto getArtistById(Long artistId);

}
