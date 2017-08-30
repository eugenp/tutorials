package com.baeldung.bean.types;

import com.baeldung.bean.types.constructor.AvailableSongsConstructorDI;
import com.baeldung.bean.types.setter.AvailableSongsSettersDI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AvailableSongsTypesTest {

    private SearchSongArtist searchSongArtist = new SearchSongArtist();

    @Test
    public void givenConstructorDIType_whenBohemian_thenQueen() {
        AvailableSongsConstructorDI availableSongs = new AvailableSongsConstructorDI(searchSongArtist);
        assertEquals("Queen", availableSongs.songByArtist("Bohemian Rhapsody"));
    }

    @Test
    public void givenSettersDIType_whenHallelujah_thenUnknown() {
        AvailableSongsSettersDI availableSongs = new AvailableSongsSettersDI();
        availableSongs.setSearchSongArtist(searchSongArtist);
        assertEquals("unknown", availableSongs.songByArtist("Thriller"));
    }
}
