package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.model.Car;
import com.baeldung.hexagonal.architecture.model.Category;
import com.baeldung.hexagonal.architecture.model.Mark;
import com.baeldung.hexagonal.architecture.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> searchCar(Mark mark, Category category, BigDecimal price, Integer constructionYear) {
        return carRepository.findBy(mark, category, price, constructionYear);
    }

}
