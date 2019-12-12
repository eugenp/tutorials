package com.baeldung.hexagonal.architecture.domain.port;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;

import java.util.List;

public interface ArtistPersistencePort {

    void addArtist(ArtistDto artistDto);

    void removeArtist(ArtistDto artistDto);

    List<ArtistDto> getAllArtists();

    ArtistDto getArtistById(Long artistId);
}
