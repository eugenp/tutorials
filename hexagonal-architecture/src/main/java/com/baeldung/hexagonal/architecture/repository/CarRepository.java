package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.model.Car;
import com.baeldung.hexagonal.architecture.model.Category;
import com.baeldung.hexagonal.architecture.model.Mark;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository {

    List<Car> findBy(Mark mark, Category category, BigDecimal price, Integer constructionYear);

}
