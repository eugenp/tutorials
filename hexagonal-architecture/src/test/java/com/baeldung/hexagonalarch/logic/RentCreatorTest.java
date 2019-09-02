package com.baeldung.hexagonalarch.logic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RentCreatorTest {
	
	private  Car availableCar;
	
	private  Car unavailableCar;

	private  Client client;

	@Before
	public void setup() {
		availableCar= new Car("BMW",true);
		  
		unavailableCar= new Car("Ford",false);
	  
		client = new Client("Ken Thompson");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void  craeteRent_ShouldTrowIllegalArgumentException() {
		RentCreator.createRent(unavailableCar, client);
	}
	
	@Test
	public void  craeteRent_ShouldCreateNewRent() {
		Rent rent = RentCreator.createRent(availableCar, client);
		assertNotNull(rent);
	}
	
}
