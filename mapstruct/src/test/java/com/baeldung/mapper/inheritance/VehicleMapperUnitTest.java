package com.baeldung.mapper.inheritance;

import static com.baeldung.util.VehicleMapperTestUtil.getCarInstance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.inheritance.mapper.CarMapper;
import com.baeldung.inheritance.mapper.VehicleMapper;
import com.baeldung.inheritance.model.Car;
import com.baeldung.inheritance.model.dto.CarDTO;
import com.baeldung.inheritance.model.dto.VehicleDTO;

public class VehicleMapperUnitTest {

    private final VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Test
    void givenVehicleToVehicleDTO_whenMaps_thenParentMappingNotWorking() {
        Car car = getCarInstance();

        VehicleDTO vehicleDTO = vehicleMapper.vehicleToDTO(car);
        Assertions.assertFalse(vehicleDTO instanceof CarDTO);

        VehicleDTO carDTO = carMapper.carToDTO(car);
        Assertions.assertTrue(carDTO instanceof CarDTO);
    }
}
