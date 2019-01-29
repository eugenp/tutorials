package com.example.orth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {
	@Autowired
	private SalesPersistenceInterface salesPersistencePort;

	public void create(String item, String region, double amount) {
		salesPersistencePort.create(item, region, amount);
	}

	public CorporateSales view(Integer salesId) {
		return salesPersistencePort.getSale(salesId);
	}
}
