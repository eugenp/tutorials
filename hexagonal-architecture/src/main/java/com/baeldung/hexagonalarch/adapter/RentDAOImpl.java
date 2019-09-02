package com.baeldung.hexagonalarch.adapter;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.baeldung.hexagonalarch.logic.Car;
import com.baeldung.hexagonalarch.logic.Client;
import com.baeldung.hexagonalarch.logic.Rent;
import com.baeldung.hexagonalarch.logic.RentCreator;
import com.baeldung.hexagonalarch.ports.IRentDAO;

public class RentDAOImpl implements IRentDAO{
	 
	@Override
	public  void createRent(Car car,  Client client) {
		Rent rent = RentCreator.createRent(car, client);
		
		try (Connection conn = new  ConfigConnection().getConnetion();
				PreparedStatement stmt = conn.prepareStatement("insert into  rent values ('"+rent.getRentTime()+"',1,1,'"+rent.getCode()+"')"))
		{ 
			stmt.executeUpdate();	
	 		
		} catch (SQLException e) {
			// implemetatation here
			 
		}	
 		
	}

}
