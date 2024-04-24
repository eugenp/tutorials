package com.baeldung.mapstruct.inheritance;

import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getBusInstance;
import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getCarInstance;
import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.runAssertionChecksForBusInstance;
import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.runAssertionChecksForCarInstance;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.mapstruct.inheritance.mapper.VehicleMapperBySubclassMapping;
import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

public class VehicleMapperBySubclassMappingUnitTest {

    private final VehicleMapperBySubclassMapping vehicleMapper = Mappers.getMapper(VehicleMapperBySubclassMapping.class);

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
