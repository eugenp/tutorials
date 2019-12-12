package com.baeldung.hexagonal.architecture.core.adapter;

import com.baeldung.hexagonal.architecture.core.port.ArtistServicePort;
import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service public class ArtistServiceAdapter implements ArtistServicePort {

        private final ArtistPersistencePort artistPersistencePort;

        public ArtistServiceAdapter(ArtistPersistencePort artistPersistencePort) {
                this.artistPersistencePort = artistPersistencePort;
        }

        @Override public void addArtist(ArtistDto artistDto) {
                artistPersistencePort.addArtist(artistDto);
        }

        @Override @Transactional public void removeArtist(ArtistDto artistDto) {
                artistPersistencePort.removeArtist(artistDto);
        }

        @Override public List<ArtistDto> getAllArtists() {
                return artistPersistencePort.getAllArtists();
        }

        @Override public ArtistDto getArtistById(Long artistId) {
                return artistPersistencePort.getArtistById(artistId);
        }
}
