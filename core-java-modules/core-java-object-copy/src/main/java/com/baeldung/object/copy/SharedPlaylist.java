package com.baeldung.object.copy;

public class SharedPlaylist extends Playlist {

    public SharedPlaylist(Playlist originalPlaylist) {
        setName(originalPlaylist.getName());
        setSongs(originalPlaylist.getSongs());
    }
}
