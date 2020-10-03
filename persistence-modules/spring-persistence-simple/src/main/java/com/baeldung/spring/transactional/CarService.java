package com.baeldung.spring.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS, readOnly = false, timeout = 30)
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Transactional(rollbackFor = IllegalArgumentException.class, noRollbackFor = EntityExistsException.class,
      rollbackForClassName = "IllegalArgumentException", noRollbackForClassName = "EntityExistsException")
    public Car save(Car car) {
        return carRepository.save(car);
    }
}
