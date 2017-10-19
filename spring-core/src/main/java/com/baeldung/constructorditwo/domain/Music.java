package com.baeldung.constructorditwo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Music {
	
	private Artist artist;
	private Song song;
	
	@Autowired
	public Music(Artist artist, Song song){
		this.artist = artist;
		this.song = song;
	}
	
	@Override
    public String toString() {
        return String.format("Artist: %s Song: %s", artist, song);
    }

	
}
