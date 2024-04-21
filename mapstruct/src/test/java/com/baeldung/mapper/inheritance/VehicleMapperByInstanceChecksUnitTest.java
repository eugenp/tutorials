package com.baeldung.mapper.inheritance;

import static com.baeldung.util.VehicleMapperTestUtil.getBusInstance;
import static com.baeldung.util.VehicleMapperTestUtil.getCarInstance;
import static com.baeldung.util.VehicleMapperTestUtil.runAssertionChecksForBusInstance;
import static com.baeldung.util.VehicleMapperTestUtil.runAssertionChecksForCarInstance;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.inheritance.mapper.VehicleMapperByInstanceChecks;
import com.baeldung.inheritance.model.Bus;
import com.baeldung.inheritance.model.Car;
import com.baeldung.inheritance.model.dto.VehicleDTO;

public class VehicleMapperByInstanceChecksUnitTest {

    private final VehicleMapperByInstanceChecks vehicleMapper = Mappers.getMapper(VehicleMapperByInstanceChecks.class);

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
