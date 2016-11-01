package com.baeldung.mdc;

public class DefaultBusinessService implements BusinessService {

	@Override
	public boolean transfer(Long amount) {
		try {
			Thread.sleep((long) (Math.random()*1000));
		} catch (InterruptedException e) {
			// should not happen
		}
		return Math.random() >= 0.5;
	}

}
