package org.springframework.tutorial.tutorial8;

import org.springframework.tutorial.tutorial7.Instrument;

public class Saxophone implements Instrument {

	@Override
	public void play() {
		System.out.println("Saxophone");
		
	}

	
}
