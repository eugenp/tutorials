package com.baeldung.boot.count.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.baeldung.boot.count.dao.CarRepository;
import com.baeldung.boot.count.data.Car;

@Service
public class CountCarService {

    @Autowired
    private CarRepository repo;

    @Autowired
    private MongoTemplate mongo;

    public List<Car> findCars() {
        return repo.findAll();
    }

    public Optional<Car> findCar(String id) {
        return repo.findById(id);
    }

    public Car insertCar(Car item) {
        return repo.insert(item);
    }

    public long getCountWithQueryAnnotation() {
        return repo.countWithAnnotation();
    }

    public long getCountWithCrudRepository() {
        return repo.count();
    }

    public long getCountBrandWithQueryMethod(String brand) {
        return repo.countByBrand(brand);
    }

    public long getCountWithExample(Car item) {
        return repo.count(Example.of(item));
    }

    public long getCountWithExampleCriteria(Car item) {
        Query query = new Query();
        query.addCriteria(Criteria.byExample(item));
        return mongo.count(query, Car.class);
    }

    public long getCountBrandWithQueryAnnotation(String brand) {
        return repo.countBrand(brand);
    }

    public long getCountBrandWithCriteria(String brand) {
        Query query = new Query();
        query.addCriteria(Criteria.where("brand")
            .is(brand));
        return mongo.count(query, Car.class);
    }
}
