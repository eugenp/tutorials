package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.visitorspattern.*;

public class VisitorsPatternUnitTest {

	@Test
	public void testAnatotitan() {

		assertEquals("very aggressive", visitorsPatter((Dino) new Anatotitan()));
	}

	@Test
	public void testEuraptor() {

		assertEquals("calm", visitorsPatter((Dino) new Euraptor()));
	}

	public static String visitorsPatter(Dino dinosaur) {
		Visitor visitor = new DinoVisitorImpl();

		return dinosaur.behavior2(visitor);
	}

}
