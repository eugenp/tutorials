package com.baeldung.mapstruct.inheritance;

import org.junit.jupiter.api.Assertions;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

public final class VehicleMapperTestUtil {

    private VehicleMapperTestUtil() {

    }

    public static Car getCarInstance() {
        Car car = new Car();
        car.setColor("Yellow");
        car.setSpeed("50 KM-PH");
        car.setTires(4);

        return car;
    }

    public static Bus getBusInstance() {
        Bus bus = new Bus();
        bus.setColor("Blue");
        bus.setSpeed("30 KM-PH");
        bus.setCapacity(50);

        return bus;
    }

    public static void runAssertionChecksForBusInstance(VehicleDTO vehicleDTO, Bus bus) {
        Assertions.assertTrue(vehicleDTO instanceof BusDTO);
        Assertions.assertEquals(bus.getCapacity(), ((BusDTO) vehicleDTO).getCapacity());
        Assertions.assertEquals(bus.getSpeed(), vehicleDTO.getSpeed());
        Assertions.assertEquals(bus.getColor(), vehicleDTO.getColor());
    }

    public static void runAssertionChecksForCarInstance(VehicleDTO vehicleDTO, Car car) {
        Assertions.assertTrue(vehicleDTO instanceof CarDTO);
        Assertions.assertEquals(car.getTires(), ((CarDTO) vehicleDTO).getTires());
        Assertions.assertEquals(car.getSpeed(), vehicleDTO.getSpeed());
        Assertions.assertEquals(car.getColor(), vehicleDTO.getColor());
    }
}
