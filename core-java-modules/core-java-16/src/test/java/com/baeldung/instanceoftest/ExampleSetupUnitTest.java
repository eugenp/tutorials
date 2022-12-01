package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.examplesetup.*;

public class ExampleSetupUnitTest {

	@Test
	public void testAnatotitan() {

		assertEquals("very aggressive", dinoBehavior(new Anatotitan()));
	}

	@Test
	public void testEuraptor() {
		assertEquals("calm", dinoBehavior(new Euraptor()));
	}

	public static String dinoBehavior(Dinosaur dinosaur) {

		if (dinosaur instanceof Anatotitan) {

			Anatotitan anatotitan = (Anatotitan) dinosaur;
			return anatotitan.behavior();
		} else if (dinosaur instanceof Euraptor) {
			Euraptor euraptor = (Euraptor) dinosaur;
			return euraptor.behavior();
		}
		return "";
	}

}
