package com.baeldung.ports;

import com.baeldung.domain.Car;

public interface CarApi {

    Car getById(int id);

    Car getByName(String name);

}
