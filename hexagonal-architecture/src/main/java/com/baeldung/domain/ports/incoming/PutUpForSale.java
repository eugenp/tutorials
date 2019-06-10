package com.baeldung.domain.ports.incoming;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;

public interface PutUpForSale {
    Car putUpForSale(NewCar newCar);
}
