package com.baeldung.mapper.inheritance;

import static com.baeldung.util.VehicleMapperTestUtil.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.inheritance.model.Bus;
import com.baeldung.inheritance.model.Car;
import com.baeldung.inheritance.model.dto.VehicleDTO;
import com.baeldung.inheritance.mapper.VehicleMapperByVisitorPattern;

public class VehicleMapperByVisitorPatternIntegrationTest {

    private final VehicleMapperByVisitorPattern vehicleMapper = Mappers.getMapper(VehicleMapperByVisitorPattern.class);

    @Test
    void givenVehicleTypeCar_whenMaps_thenMappedToCarDTOCorrectly() {
        Car car = getCarInstance();

        VehicleDTO vehicleDTO = vehicleMapper.mapToVehicleDTO(car);
        runAssertionChecksForCarInstance(vehicleDTO, car);
    }

    @Test
    void givenVehicleTypeBus_whenMaps_thenMappedToBusDTOCorrectly() {
        Bus bus = getBusInstance();

        VehicleDTO vehicleDTO = vehicleMapper.mapToVehicleDTO(bus);
        runAssertionChecksForBusInstance(vehicleDTO, bus);
    }

}
