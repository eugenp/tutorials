package com.hex.adapter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hex.domain.Car;
import com.hex.port.CarRepositoryPort;


@Service
public class CarServiceAdapter implements CarRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void create(String model, String make, long color) {
        Car car = new Car();
        car.setModel(model);
        car.setMake(make);
        car.setColor(color);
       
        entityManager.persist(car);
    }


	@Override
	public Car getCar(Integer carId) {
		// TODO Auto-generated method stub
		return null;
	}
}