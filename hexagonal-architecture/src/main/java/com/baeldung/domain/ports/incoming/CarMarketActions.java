package com.baeldung.domain.ports.incoming;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;

import java.math.BigDecimal;
import java.util.List;

public interface CarMarketActions {
    boolean buyCar(long carId, BigDecimal propsedAmount);
    List<Car> showAvailableCars();
    Car putUpForSale(NewCar newCar);
}
