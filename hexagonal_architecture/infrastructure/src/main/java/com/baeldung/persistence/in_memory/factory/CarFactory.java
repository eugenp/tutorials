package com.baeldung.persistence.in_memory.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.domain.repostory.CarRepositoryPort;
import com.baeldung.persistence.DefaultCarServiceAdapter;
import com.baeldung.persistence.enums.CarEnum;
import com.baeldung.persistence.in_memory.MockCarServiceAdapter;
import com.baeldung.persistence.in_mongo.MongoDBCarServiceAdapter;

@Component
public class CarFactory {

	@Autowired
	private MockCarServiceAdapter mockCarServiceAdapter;
	
	@Autowired
	private MongoDBCarServiceAdapter mongoDBCarServiceAdapter;
	
	private DefaultCarServiceAdapter defaultServiceAdapter;

	public CarRepositoryPort getConnection(String typeConnection) {
			
	      if(typeConnection.equalsIgnoreCase(CarEnum.MONGODB.name())){
	         return  mongoDBCarServiceAdapter;
	         
	      } else if(typeConnection.equalsIgnoreCase(CarEnum.MEMORY.name())){
	         return mockCarServiceAdapter;    
	      }
	     
		return defaultServiceAdapter;
	}
}