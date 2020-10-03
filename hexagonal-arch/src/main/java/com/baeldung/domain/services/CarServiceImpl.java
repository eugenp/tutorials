package com.baeldung.domain.services;

import com.baeldung.domain.entities.Car;
import com.baeldung.domain.ports.in.CarService;
import com.baeldung.domain.ports.out.CarDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


public class CarServiceImpl implements CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarDao carDao;

    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public Car getCar(String vin) {
        Optional<Car> car = carDao.findById(vin);
        if (car.isPresent()) {
            return car.get();
        }
        logger.info("Car not found");
        return null;
    }

    @Override
    public Boolean saveCar(Car car) {
        // check the VIN format
        // some code ...

        // check model year
        if (car.getModelYear() > LocalDate.now().getYear() + 1) {
            logger.error("Invalid model year {}", car.getModelYear());
            return false;
        }

        try {
            carDao.save(car);
            return true;
        }
        catch (Exception e) {
            logger.error("Error:", e);
            return false;
        }
    }
}
