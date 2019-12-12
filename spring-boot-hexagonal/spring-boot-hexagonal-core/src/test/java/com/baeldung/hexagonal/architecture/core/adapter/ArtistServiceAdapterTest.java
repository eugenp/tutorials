package com.baeldung.hexagonal.architecture.core.adapter;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    @Mock
    private List<ArtistDto> mockArtistDtoList;

    @Test
    public void givenArtist_whenAdd_thenAddPortCalled() {
        final ArtistDto mockArtistDto = mock(ArtistDto.class);

        artistServicePort.addArtist(mockArtistDto);

        verify(artistPersistencePort, only()).addArtist(mockArtistDto);
    }

    @Test
    public void givenArtist_whenRemove_thenRemovePortCalled() {
        final ArtistDto mockArtistDto = mock(ArtistDto.class);

        artistServicePort.removeArtist(mockArtistDto);

        verify(artistPersistencePort, only()).removeArtist(mockArtistDto);
    }

    @Test
    public void givenCallToAllArtists_whenNothingSpecified_thenGetAllArtistsPortCalled() {
        when(artistPersistencePort.getAllArtists()).thenReturn(mockArtistDtoList);

        final List<ArtistDto> allArtistDtos = artistServicePort.getAllArtists();

        assertThat(allArtistDtos).isSameAs(mockArtistDtoList);
        verify(artistPersistencePort, only()).getAllArtists();
    }

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