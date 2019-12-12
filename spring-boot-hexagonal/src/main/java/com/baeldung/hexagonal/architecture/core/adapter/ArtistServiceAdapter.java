package com.baeldung.hexagonal.architecture.core.adapter;

import com.baeldung.hexagonal.architecture.core.port.ArtistServicePort;
import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceAdapter implements ArtistServicePort {

    private final ArtistPersistencePort artistPersistencePort;

    public ArtistServiceAdapter(ArtistPersistencePort artistPersistencePort) {
        this.artistPersistencePort = artistPersistencePort;
    }

    @Override
    public ArtistDto getArtistById(Long artistId) {
        return artistPersistencePort.getArtistById(artistId);
    }
}
