package com.baeldung.mapstruct.inheritance;

import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getBusInstance;
import static com.baeldung.mapstruct.inheritance.VehicleMapperTestUtil.getCarInstance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.mapstruct.inheritance.mapper.VehicleMapperBySubclassMapping;
import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

public class VehicleMapperBySubclassMappingUnitTest {

    private final VehicleMapperBySubclassMapping vehicleMapper = Mappers.getMapper(VehicleMapperBySubclassMapping.class);

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
