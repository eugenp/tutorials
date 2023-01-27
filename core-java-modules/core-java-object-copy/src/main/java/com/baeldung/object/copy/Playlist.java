package com.baeldung.object.copy;

import java.util.Set;

import lombok.Data;

@Data
public class Playlist {

	private String name;
	private Set<Song> songs;

}
