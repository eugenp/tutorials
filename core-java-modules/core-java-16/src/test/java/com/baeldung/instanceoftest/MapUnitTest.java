package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baeldung.instanceofalternative.mapalt.*;

public class MapUnitTest {

	@Test
	public void testAnatotitan() {

		assertEquals("very aggressive", dinoBehavior(new Anatotitan()));
	}

	@Test
	public void testEuraptor() {

		assertEquals("calm", dinoBehavior(new Euraptor()));
	}

	public static String dinoBehavior(Dinosaur dinosaur) {
		Map<Class<? extends Dinosaur>, String> response = new HashMap<Class<? extends Dinosaur>, String>();

		response.put(dinosaur.getClass(), dinosaur.behavior());
		return response.get(dinosaur.getClass());
	}

}
