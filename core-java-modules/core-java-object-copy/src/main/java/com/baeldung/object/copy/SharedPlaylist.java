package com.baeldung.object.copy;

public class SharedPlaylist extends Playlist {

	public SharedPlaylist(Playlist originalPlaylist) {
		// initialize with same name
		setName(originalPlaylist.getName());
		
		// shallow-copy: assigns a reference to original collection of play-list's songs
		setSongs(originalPlaylist.getSongs());
	}
}
