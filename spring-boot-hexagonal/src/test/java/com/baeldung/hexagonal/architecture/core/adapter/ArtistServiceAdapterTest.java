package com.baeldung.hexagonal.architecture.core.adapter;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceAdapterTest {

    @InjectMocks
    private ArtistServiceAdapter artistServicePort;

    @Mock
    private ArtistPersistencePort artistPersistencePort;

    @Test
    public void givenArtistId_whenGetArtistsById_thenGetArtistByIdPortCalled() {
        final Long mockArtistId = 1L;
        final ArtistDto mockArtistDto = mock(ArtistDto.class);
        when(artistPersistencePort.getArtistById(mockArtistId)).thenReturn(mockArtistDto);

        final ArtistDto artistDto = artistServicePort.getArtistById(mockArtistId);

        assertThat(artistDto).isSameAs(mockArtistDto);
        verify(artistPersistencePort, only()).getArtistById(mockArtistId);
    }
}