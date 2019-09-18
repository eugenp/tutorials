package com.baeldung.persistence.in_memory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.domain.Car;
import com.baeldung.domain.repostory.CarRepositoryPort;

@Service
public class MockCarServiceAdapter implements CarRepositoryPort {

	   @PersistenceContext
	    private EntityManager entityManager;

	    @Override
	    public List<Car> listAllCars() {
	           TypedQuery<Car> query = entityManager.createQuery("FROM Car", Car.class);
		   List<Car> cars = query.getResultList();		  
		return cars;
	    }
		
	    @Transactional
	    @Override
	    public void saveCar(String brand, Integer year) {
	        entityManager.persist(new Car(brand,year));
	    }
}
