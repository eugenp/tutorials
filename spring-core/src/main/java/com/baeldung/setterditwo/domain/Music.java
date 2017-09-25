package com.baeldung.setterditwo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Music {
	private Artist artist;
	private Song song;
	

	@Autowired
	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	@Autowired
	public void setSong(Song song) {
		this.song = song;
	}
	
	public Artist getArtist() {
		return artist;
	}
	
	public Song getSong() {
		return song;
	}
	
	public Music() {
		
	}
	
	  @Override
	    public String toString() {
	        return String.format("Artist: %s Song: %s", artist, song);
	    }
	
	
}
