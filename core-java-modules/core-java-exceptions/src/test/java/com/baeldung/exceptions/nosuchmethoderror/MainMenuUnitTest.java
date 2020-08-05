package com.baeldung.exceptions.nosuchmethoderror;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class MainMenuUnitTest {

	@Test
	void testgetSpecials() {
		assertNotNull(MainMenu.getSpecials());
	} 
 
}
