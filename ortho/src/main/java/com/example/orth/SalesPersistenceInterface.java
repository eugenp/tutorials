package com.example.orth;

public interface SalesPersistenceInterface {
	String create(String item, String region, double amount);

	CorporateSales getSale(Integer salesId);
}