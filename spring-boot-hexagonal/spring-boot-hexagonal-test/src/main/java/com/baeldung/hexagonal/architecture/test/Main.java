package com.baeldung.hexagonal.architecture.test;

import com.baeldung.hexagonal.architecture.core.adapter.ArtistServiceAdapter;
import com.baeldung.hexagonal.architecture.core.port.ArtistServicePort;
import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.port.ArtistPersistencePort;

import java.util.Collections;
import java.util.List;

/**
 * This is just a test application. The goal is not to use test frameworks. The goal is just to show how easy it is to implement an adapter that is specifically designed to test nothing else but the core of the application.
 * <p>
 * Please run this with -ea JVM command.
 */
public class Main {

        public static void main(String[] args) {
                final ArtistServicePort artistServicePort = new ArtistServiceAdapter(createMockArtistPersistencePort());
                final ArtistDto artistDto =  ArtistDto.builder().name("Alicia Keys").build();
                artistServicePort.addArtist(artistDto);
                artistServicePort.removeArtist(artistDto);
                final List<ArtistDto> allArtistDtos = artistServicePort.getAllArtists();
                final ArtistDto artistDtoById = artistServicePort.getArtistById(1L);
                assert allArtistDtos.size() == 1;
                assert allArtistDtos.get(0).getName().equals("Beyonce");
                assert artistDtoById.getName().equals("Macklemore");
        }

        private static ArtistPersistencePort createMockArtistPersistencePort() {
                return new ArtistPersistencePort() {
                        @Override public void addArtist(ArtistDto artistDto) {

                        }

                        @Override public void removeArtist(ArtistDto artistDto) {

                        }

                        @Override public List<ArtistDto> getAllArtists() {
                                final ArtistDto artistDto = ArtistDto.builder().name("Beyonce").build();
                                return Collections.singletonList(artistDto);
                        }

                        @Override public ArtistDto getArtistById(Long artistId) {
                                return ArtistDto.builder().name("Macklemore").build();
                        }
                };
        }
}
