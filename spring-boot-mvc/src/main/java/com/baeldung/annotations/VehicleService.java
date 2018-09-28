package com.baeldung.annotations;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Async
    public void repairCar() {
    }

}
