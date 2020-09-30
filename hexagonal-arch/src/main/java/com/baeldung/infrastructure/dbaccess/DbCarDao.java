package com.baeldung.infrastructure.dbaccess;

import com.baeldung.domain.entities.Car;
import com.baeldung.domain.ports.out.CarDao;
import com.baeldung.infrastructure.config.SpringJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DbCarDao implements CarDao {

    @Autowired
    private SpringJpa carDao;

    @Override
    public Optional<Car> findById(String vin) {
        return carDao.findById(vin);
    }

    @Override
    public void save(Car car) {
        carDao.save(car);
    }

    @Override
    public void deleteById(String vin) {
        carDao.deleteById(vin);
    }
}
