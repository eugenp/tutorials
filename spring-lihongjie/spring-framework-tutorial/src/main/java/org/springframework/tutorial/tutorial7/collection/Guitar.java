package org.springframework.tutorial.tutorial7.collection;

import org.springframework.tutorial.tutorial7.Instrument;

public class Guitar implements Instrument {

	@Override
	public void play() {
		System.out.println("Guitar");
	}

}
