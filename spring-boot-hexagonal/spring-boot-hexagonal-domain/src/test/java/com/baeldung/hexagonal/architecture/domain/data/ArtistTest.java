package com.baeldung.hexagonal.architecture.domain.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArtistTest {

    @Test
    public void givenName_whenArtistWithName_thenNameIsSet() {
        final ArtistDto artistDto = ArtistDto.builder()
            .name("Humberto")
            .build();

        assertThat(artistDto.getName()).isEqualTo("Humberto");
    }

    @Test
    public void givenTwoArtists_whenSameName_thenEqual() {
        final ArtistDto artistDto1 = ArtistDto.builder()
            .name("Humberto")
            .build();
        final ArtistDto artistDto2 = ArtistDto.builder()
            .name("Humberto")
            .build();

        assertThat(artistDto1).isEqualTo(artistDto2);
    }

    @Test
    public void givenTwoArtists_whenDifferentName_thenNotEqual() {
        final ArtistDto artistDto1 = ArtistDto.builder()
            .name("Humberto")
            .build();
        final ArtistDto artistDto2 = ArtistDto.builder()
            .name("Franscesco")
            .build();

        assertThat(artistDto1).isNotEqualTo(artistDto2);
    }

    @Test
    public void givenTwoArtists_whenSameName_thenSameHashCode() {
        final ArtistDto artistDto1 = ArtistDto.builder()
            .name("Humberto")
            .build();
        final ArtistDto artistDto2 = ArtistDto.builder()
            .name("Humberto")
            .build();

        assertThat(artistDto1.hashCode()).isEqualTo(artistDto2.hashCode());
    }

    @Test
    public void givenTwoArtists_whenDifferentName_thenNotSameHashCode() {
        final ArtistDto artistDto1 = ArtistDto.builder()
            .name("Humberto")
            .build();
        final ArtistDto artistDto2 = ArtistDto.builder()
            .name("Francesco")
            .build();

        assertThat(artistDto1.hashCode()).isNotEqualTo(artistDto2.hashCode());
    }

    @Test
    public void givenArtist_whenToString_thenSeeADescriptiveString() {
        final ArtistDto artistDto1 = ArtistDto.builder()
            .name("Humberto")
            .build();

        assertThat(artistDto1.toString()).isEqualTo("ArtistDto(name=Humberto)");
    }
}