package com.baeldung.mapstruct.inheritance;

import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;
import com.baeldung.mapstruct.inheritance.mapper.VehicleMapperByVisitorPattern;

public class VehicleMapperByVisitorPatternUnitTest {

    private final VehicleMapperByVisitorPattern vehicleMapper = Mappers.getMapper(VehicleMapperByVisitorPattern.class);

    @Test
    void whenVehicleTypeIsCar_thenMappedToCarDTOCorrectly() {
        Car car = getCarInstance();

        VehicleDTO vehicleDTO = vehicleMapper.mapToVehicleDTO(car);
        runAssertionChecksForCarInstance(vehicleDTO, car);
    }

    @Test
    void whenVehicleTypeIsBus_thenMappedToBusDTOCorrectly() {
        Bus bus = getBusInstance();

        VehicleDTO vehicleDTO = vehicleMapper.mapToVehicleDTO(bus);
        runAssertionChecksForBusInstance(vehicleDTO, bus);
    }

}
