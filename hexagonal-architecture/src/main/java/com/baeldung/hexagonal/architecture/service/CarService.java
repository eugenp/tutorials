package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.model.Car;
import com.baeldung.hexagonal.architecture.model.Category;
import com.baeldung.hexagonal.architecture.model.Mark;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {

    List<Car> searchCar(Mark mark, Category category, BigDecimal price, Integer constructionYear);

}
