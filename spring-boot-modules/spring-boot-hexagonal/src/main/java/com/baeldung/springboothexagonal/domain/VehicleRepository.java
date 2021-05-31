package com.baeldung.springboothexagonal.domain;

import java.util.List;

public interface VehicleRepository {

    List<Vehicle> loadAllVehicles();
}
