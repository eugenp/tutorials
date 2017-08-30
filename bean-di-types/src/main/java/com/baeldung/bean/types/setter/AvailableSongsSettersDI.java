package com.baeldung.bean.types.setter;

import com.baeldung.bean.types.SearchSongArtist;

public class AvailableSongsSettersDI {

    private SearchSongArtist searchSongArtist;

    public void setSearchSongArtist(SearchSongArtist searchSongArtist) {
        this.searchSongArtist = searchSongArtist;
    }

    public String songByArtist(String songTitle) {
        return searchSongArtist.find(songTitle);
    }
}
