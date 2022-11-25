package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baeldung.mapalt.*;

public class MapTest {
	
	
	
	 Map<Class<? extends Dinosaur>, String> response = 
	            new HashMap<Class<? extends Dinosaur>, String>();
	    Dinosaur anatotitan = new Anatotitan();
	    Dinosaur euraptor = new Euraptor();
	    
	   

	@Test
	public void testAnatotitan() {
		 response.put(Anatotitan.class, anatotitan.behavior());
		  

		assertEquals("very aggressive", response.get(Anatotitan.class));
	}

	@Test
	public void testEuraptor() {
		response.put(Euraptor.class, euraptor.behavior());
		  

		assertEquals("calm", response.get(Euraptor.class));
	}

	

}
