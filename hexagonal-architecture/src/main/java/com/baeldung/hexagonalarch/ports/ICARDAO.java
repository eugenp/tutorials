package com.baeldung.hexagonalarch.ports;

 
import com.baeldung.hexagonalarch.logic.Car;

public interface ICARDAO {
	
	  Car findCarByMake(String make);
	
}
