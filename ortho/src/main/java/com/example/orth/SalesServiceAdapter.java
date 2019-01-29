package com.example.orth;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class SalesServiceAdapter implements SalesPersistenceInterface {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public String create(String item, String region, double amount) {
		CorporateSales sales = new CorporateSales(item,region,amount);
		entityManager.persist(sales);
		return item + " " + region + " ";
	}

	@Override
	public CorporateSales getSale(Integer salesId) {
		return entityManager.find(CorporateSales.class, salesId);
	}
}