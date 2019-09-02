package com.baeldung.hexagonalarch.ports;

import com.baeldung.hexagonalarch.logic.Client;

public interface IClientDAO {
	
	Client findClientByName(String name); 

}
