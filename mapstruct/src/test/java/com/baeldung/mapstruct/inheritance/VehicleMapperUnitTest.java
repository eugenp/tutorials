package com.baeldung.mapstruct.inheritance;

import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getCarInstance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.mapstruct.inheritance.mapper.CarMapper;
import com.baeldung.mapstruct.inheritance.mapper.VehicleMapper;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

public class VehicleMapperUnitTest {

    private final VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Test
    void whenVehicleTypeIsCar_thenBaseMapperNotMappingToSubclass() {
        Car car = getCarInstance();

        VehicleDTO vehicleDTO = vehicleMapper.vehicleToDTO(car);
        Assertions.assertFalse(vehicleDTO instanceof CarDTO);
        Assertions.assertTrue(vehicleDTO instanceof VehicleDTO);

        VehicleDTO carDTO = carMapper.carToDTO(car);
        Assertions.assertTrue(carDTO instanceof CarDTO);
    }
}
