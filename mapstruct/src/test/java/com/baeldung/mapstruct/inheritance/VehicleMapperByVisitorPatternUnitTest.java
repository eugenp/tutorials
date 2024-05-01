package com.baeldung.mapstruct.inheritance;

import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getBusInstance;
import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getCarInstance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;
import com.baeldung.mapstruct.inheritance.mapper.VehicleMapperByVisitorPattern;

public class VehicleMapperByVisitorPatternUnitTest {

    private final VehicleMapperByVisitorPattern vehicleMapper = Mappers.getMapper(VehicleMapperByVisitorPattern.class);

    @Test
    void whenVehicleTypeIsCar_thenMappedToCarDTOCorrectly() {
        Car car = getCarInstance();

        VehicleDTO vehicleDTO = vehicleMapper.mapToVehicleDTO(car);
        Assertions.assertTrue(vehicleDTO instanceof CarDTO);
        Assertions.assertEquals(car.getTires(), ((CarDTO) vehicleDTO).getTires());
        Assertions.assertEquals(car.getSpeed(), vehicleDTO.getSpeed());
        Assertions.assertEquals(car.getColor(), vehicleDTO.getColor());
    }

    @Test
    void whenVehicleTypeIsBus_thenMappedToBusDTOCorrectly() {
        Bus bus = getBusInstance();

        VehicleDTO vehicleDTO = vehicleMapper.mapToVehicleDTO(bus);
        Assertions.assertTrue(vehicleDTO instanceof BusDTO);
        Assertions.assertEquals(bus.getCapacity(), ((BusDTO) vehicleDTO).getCapacity());
        Assertions.assertEquals(bus.getSpeed(), vehicleDTO.getSpeed());
        Assertions.assertEquals(bus.getColor(), vehicleDTO.getColor());
    }
}
