package com.baeldung.object.copy;

import java.util.HashSet;

public class InspiredPlaylist extends Playlist {

public InspiredPlaylist(Playlist originalPlaylist) {
	// initialize with same name
	setName(originalPlaylist.getName());
	
	// deep-copy: create a new set with references to immutable song objects as in original play-list
	setSongs(new HashSet<>(originalPlaylist.getSongs()));
}
}
