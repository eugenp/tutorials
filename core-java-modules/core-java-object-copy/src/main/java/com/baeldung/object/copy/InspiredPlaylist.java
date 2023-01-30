package com.baeldung.object.copy;

import java.util.HashSet;

public class InspiredPlaylist extends Playlist {

    public InspiredPlaylist(Playlist originalPlaylist) {
        setName(originalPlaylist.getName());
        setSongs(new HashSet<>(originalPlaylist.getSongs()));
    }
}
