package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.model.Airplane;
import com.baeldung.model.Car;
import com.baeldung.model.Transport;

public class TypeComparisonTest {
	
	@Test
	public void carAndAirplaneTest() {
		
		Object car = new Car();
		assertEquals(car instanceof Airplane, false);
		
		Object airplane = new Airplane();
		assertEquals(airplane instanceof Car, false);
	}
	
	@Test
	public void carAndAirplaneAreTransporTest() {
		
		Car car = new Car();
		assertEquals(car instanceof Transport, true);
		
		Airplane airplane = new Airplane();
		assertEquals(airplane instanceof Transport, true);
	}
	
	@Test
	public void transporCanBeCarOrAirplaneTest() {
		
		Transport transport1 = new Car();
		assertEquals(transport1 instanceof Car, true);
		assertEquals(transport1 instanceof Airplane, false);
		
		Transport transport2 = new Airplane();
		assertEquals(transport2 instanceof Car, false);
		assertEquals(transport2 instanceof Airplane, true);
	}

}
