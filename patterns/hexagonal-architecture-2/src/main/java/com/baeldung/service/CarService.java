package com.baeldung.service;

import com.baeldung.domain.Car;
import com.baeldung.ports.CarDao;

public class CarService {

    private CarDao dao;

    public CarService(CarDao dao) {
        this.dao = dao;
    }

    public Car find(int id) {
        return dao.getById(id);
    }

    public Car find(String name) {
        return dao.getByName(name);
    }

}
