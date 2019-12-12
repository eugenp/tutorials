package com.baeldung.hexagonal.architecture.jpa.adapter;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.exception.ArtistNotFoundException;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import com.baeldung.hexagonal.architecture.jpa.model.ArtistEntity;
import com.baeldung.hexagonal.architecture.jpa.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { ArtistJpaAdapter.class, ArtistPersistencePort.class })
public class ArtistJpaAdapterTest {

    private static final String TEST_ARTIST_NAME = "Ed Sheeran";

    @Autowired
    private ArtistPersistencePort artistPersistencePort;

    @MockBean
    private ArtistRepository artistRepository;

    @Test
    void givenArtisId_whenCallingGetArtistById_thenFindByIdToRepository() {
        final ArtistEntity testArtist = ArtistEntity.builder()
            .name(TEST_ARTIST_NAME)
            .build();
        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));

        final ArtistDto artistDtoById = artistPersistencePort.getArtistById(1L);

        verify(artistRepository, only()).findById(1L);
        assertThat(artistDtoById.getName()).isEqualTo(TEST_ARTIST_NAME);
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetArtistById_thenFindByIdToRepositoryFails() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ArtistNotFoundException.class, () -> artistPersistencePort.getArtistById(1L));
    }
}