package com.baeldung.hexagonalarch.ports;
 
import com.baeldung.hexagonalarch.logic.Car;
import com.baeldung.hexagonalarch.logic.Client;
 
public interface IRentDAO {
	
	void createRent(Car car,  Client client);

}
