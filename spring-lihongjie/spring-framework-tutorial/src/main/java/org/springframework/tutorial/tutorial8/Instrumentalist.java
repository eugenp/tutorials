package org.springframework.tutorial.tutorial8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.tutorial.tutorial7.Instrument;

public class Instrumentalist {

	private String song;

	private Instrument instrument;

	public String getSong() {
		System.out.println(song);
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public Instrument getInstrument() {
		
		return instrument;
	}

	@Autowired
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
	
}
