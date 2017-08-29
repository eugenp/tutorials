package com.baeldung.bean.types.constructor;

import com.baeldung.bean.types.SearchSongArtist;

public class AvailableSongs {

    private SearchSongArtist searchSongArtist;

    public AvailableSongs(SearchSongArtist searchSongArtist) {
        this.searchSongArtist = searchSongArtist;
    }
}
