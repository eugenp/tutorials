package com.baeldung.ports;

import com.baeldung.domain.Car;

public interface CarDao {

    Car getById(int id);

    Car getByName(String name);

}
