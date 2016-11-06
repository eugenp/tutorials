package com.baeldung.mdc;

/**
 * A fake {@link BusinessService} simulating an actual one.
 */
public class DefaultBusinessService implements BusinessService {

	public boolean transfer(long amount) {
		try {
			// let's pause randomly to properly simulate an actual system. 
			Thread.sleep((long) (500 + Math.random()*500));
		} catch (InterruptedException e) {
			// should never happen
		}
		// let's simulate both failing and successful transfers 
		return Math.random() >= 0.25;
	}

}
