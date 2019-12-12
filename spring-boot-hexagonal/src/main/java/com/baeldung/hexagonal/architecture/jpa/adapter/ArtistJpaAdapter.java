package com.baeldung.hexagonal.architecture.jpa.adapter;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.exception.ArtistNotFoundException;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import com.baeldung.hexagonal.architecture.jpa.model.ArtistEntity;
import com.baeldung.hexagonal.architecture.jpa.repository.ArtistRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ArtistJpaAdapter implements ArtistPersistencePort {

    private ArtistRepository artistRepository;

    public ArtistJpaAdapter(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @SneakyThrows
    @Override
    public ArtistDto getArtistById(Long artistId) {
        return getArtist(artistRepository.findById(artistId)
            .orElseThrow((Supplier<Throwable>) () -> new ArtistNotFoundException(artistId)));
    }

    private ArtistDto getArtist(ArtistEntity artistEntity) {
        return ArtistDto.builder()
            .name(artistEntity.getName())
            .build();
    }

}
