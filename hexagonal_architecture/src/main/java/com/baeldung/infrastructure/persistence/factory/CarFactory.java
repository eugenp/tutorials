package com.baeldung.infrastructure.persistence.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.domain.enums.CarEnum;
import com.baeldung.domain.repostory.CarRepositoryPort;
import com.baeldung.infrastructure.persistence.factory.defaultAdapter.DefaultCarServiceAdapter;
import com.baeldung.infrastructure.persistence.in_memory.service.MockCarServiceAdapter;
import com.baeldung.infrastructure.persistence.mongo.service.MongoDBCarServiceAdapter;

@Component
public class CarFactory {

	@Autowired
	private MockCarServiceAdapter mockCarServiceAdapter;
	
	@Autowired
	private MongoDBCarServiceAdapter mongoDBCarServiceAdapter;
	
	@Autowired
	private DefaultCarServiceAdapter defaultServiceAdapter;

	public CarRepositoryPort getConnection(String typeConnection) {
			
	      if(typeConnection.equalsIgnoreCase(CarEnum.MEMORY.name())){
	         return mockCarServiceAdapter;
	      } else if(typeConnection.equalsIgnoreCase(CarEnum.MONGODB.name())){
	         return mongoDBCarServiceAdapter;
	      }
		return defaultServiceAdapter;
	}
}