package com.baeldung.bean.types.constructor;

import com.baeldung.bean.types.SearchSongArtist;

public class AvailableSongsConstructorDI {

    private SearchSongArtist searchSongArtist;

    public AvailableSongsConstructorDI(SearchSongArtist searchSongArtist) {
        this.searchSongArtist = searchSongArtist;
    }

    public String songByArtist(String songTitle) {
        return searchSongArtist.find(songTitle);
    }
}
