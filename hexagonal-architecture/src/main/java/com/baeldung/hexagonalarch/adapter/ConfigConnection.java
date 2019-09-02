package com.baeldung.hexagonalarch.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 
public class ConfigConnection {
	
	private  final String user="postgres";
	
	private  final String password="postgres";
	
	
	public  Connection getConnetion() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/rentcar",user,password);	
		
	}

}
