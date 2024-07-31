package com.baeldung.spring.jinq.repositories;

import java.util.List;
import java.util.Optional;

import org.jinq.orm.stream.JinqStream;
import org.jinq.tuples.Pair;
import org.jinq.tuples.Tuple3;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.jinq.entities.Car;
import com.baeldung.spring.jinq.entities.Manufacturer;

@Repository
public class CarRepositoryImpl extends BaseJinqRepositoryImpl<Car> implements CarRepository {

    @Override
    public Optional<Car> findByModel(String model) {
        return stream().where(c -> c.getModel()
            .equals(model))
            .findFirst();
    }

    @Override
    public List<Car> findByModelAndDescription(String model, String desc) {
        return stream().where(c -> c.getModel()
            .equals(model)
            && c.getDescription()
                .contains(desc))
            .toList();
    }

    @Override
    public List<Tuple3<String, Integer, String>> findWithModelYearAndEngine() {
        return stream().select(c -> new Tuple3<>(c.getModel(), c.getYear(), c.getEngine()))
            .toList();
    }

    @Override
    public Optional<Manufacturer> findManufacturerByModel(String model) {
        return stream().where(c -> c.getModel()
            .equals(model))
            .select(c -> c.getManufacturer())
            .findFirst();
    }

    @Override
    public List<Pair<Manufacturer, Car>> findCarsPerManufacturer() {
        return streamOf(Manufacturer.class).join(m -> JinqStream.from(m.getCars()))
            .toList();
    }

    @Override
    public long countCarsByModel(String model) {
        return stream().where(c -> c.getModel()
            .equals(model))
            .count();
    }

    @Override
    public List<Car> findAll(int skip, int limit) {
        return stream().skip(skip)
            .limit(limit)
            .toList();
    }

    @Override
    protected Class<Car> entityType() {
        return Car.class;
    }

}
