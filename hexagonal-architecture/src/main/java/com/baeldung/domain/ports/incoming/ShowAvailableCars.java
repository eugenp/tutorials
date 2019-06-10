package com.baeldung.domain.ports.incoming;

import com.baeldung.domain.ports.dtos.Car;

import java.util.List;

public interface ShowAvailableCars {
    List<Car> showAvailableCars();
}
