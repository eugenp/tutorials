package com.baeldung.hexagonal.architecture.jpa.adapter;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.exception.ArtistNotFoundException;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import com.baeldung.hexagonal.architecture.jpa.model.ArtistEntity;
import com.baeldung.hexagonal.architecture.jpa.repository.ArtistRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service public class ArtistJpaAdapter implements ArtistPersistencePort {

        private ArtistRepository artistRepository;

        public ArtistJpaAdapter(ArtistRepository artistRepository) {
                this.artistRepository = artistRepository;
        }

        @Override public void addArtist(ArtistDto artistDto) {
                final ArtistEntity artistEntity = getArtistEntity(artistDto);
                artistRepository.save(artistEntity);
        }

        @Override public void removeArtist(ArtistDto artistDto) {
                artistRepository.deleteAllByName(artistDto.getName());
        }

        @Override public List<ArtistDto> getAllArtists() {
                return artistRepository.findAll().stream().map(this::getArtist).collect(Collectors.toList());
        }

        @SneakyThrows @Override public ArtistDto getArtistById(Long artistId) {
                return getArtist(artistRepository.findById(artistId).orElseThrow((Supplier<Throwable>) () -> new ArtistNotFoundException(artistId)));
        }

        private ArtistEntity getArtistEntity(ArtistDto artistDto) {
                return ArtistEntity.builder().name(artistDto.getName()).build();
        }

        private ArtistDto getArtist(ArtistEntity artistEntity) {
                return ArtistDto.builder().name(artistEntity.getName()).build();
        }

}
