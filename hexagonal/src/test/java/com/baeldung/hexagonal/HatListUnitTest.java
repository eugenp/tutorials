package com.baeldung.hexagonal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//primary adapter as a test class 
public class HatListUnitTest {

	@Test
	public void saveAndRetrieveHat() {
		HatList hatList = new HatListCore();
		hatList.setHatPersistence(new InMemoryHatPersistence());
		HatStore hatStore = hatList.getHatStore();
		hatStore.add("first", "top hat");
		assertEquals("top hat", hatStore.getHatFor("first"));
	}
}
