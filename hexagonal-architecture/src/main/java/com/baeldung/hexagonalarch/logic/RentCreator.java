package com.baeldung.hexagonalarch.logic;

import java.time.LocalDateTime;

public class RentCreator {
	
	public static Rent createRent(Car car,  Client client) {
		if (car.isAvailable()) {
			return new  Rent(LocalDateTime.now(),car,client,"abc123");
		}
		throw new IllegalArgumentException("Car must be Available!!!");
	}

}
