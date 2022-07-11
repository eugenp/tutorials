package com.baeldung.boot.count.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.count.dao.CarRepository;
import com.baeldung.boot.count.data.Car;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepo;

    @Autowired
    private MongoTemplate mongo;

    @GetMapping("/{id}")
    public Optional<Car> getCar(@PathVariable String id) {
        return carRepo.findById(id);
    }

    @PostMapping
    public Car postCar(@RequestBody Car item) {
        return carRepo.insert(item);
    }

    @GetMapping("/count/{brand}")
    public Long getCountCarBrand(@PathVariable String brand) {
        return carRepo.countByBrand(brand);
    }

    @GetMapping("/count2/{brand}")
    public Long getCountCarBrand2(@PathVariable String brand) {
        return carRepo.countBrand(brand);
    }

    @GetMapping("/count")
    public Long getCountCar() {
        return carRepo.countWithAnnotation();
    }

    @GetMapping("/count2")
    public Long getCountCar2() {
        // default do repo
        return carRepo.count();
    }

    @PostMapping("/count")
    public Long postCount(@RequestBody Car item) {
        return carRepo.count(Example.of(item));
    }

    @PostMapping("/count/criteria")
    public Long postCountCriteria(@RequestBody Car item) {
        Query query = new Query();
        query.addCriteria(Criteria.byExample(item));
        return mongo.count(query, Car.class);
    }

    @GetMapping("/count/criteria/{brand}")
    public Long getCountCarCriteria(@PathVariable String brand) {
        Query query = new Query();
        query.addCriteria(Criteria.where("brand")
            .is(brand));
        return mongo.count(query, Car.class);
    }
}
