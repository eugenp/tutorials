package com.baeldung.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.list.entity.Car;
import com.baeldung.list.entity.CarDto;
import com.baeldung.list.entity.ManufacturingPlantDto;

public class SourcePropsToListElementsUnitTest {
    @Test
    void whenUseMappingExpression_thenConvertCarToCarDto() {
        Car car = createCarObject();

        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        assertEquals("Morris", carDto.getMake());
        assertEquals("Mini", carDto.getModel());
        assertEquals(1969, carDto.getYear());
        assertEquals(4, carDto.getNumberOfSeats());

        validateTargetList(carDto.getManufacturingPlantDtos());
    }

    @Test
    void whenUsingDecorator_thenConvertCarToCarDto() {
        Car car = createCarObject();

        CarDto carDto = CustomCarMapper.INSTANCE.carToCarDto(car);

        assertEquals("Morris", carDto.getMake());
        assertEquals("Mini", carDto.getModel());
        assertEquals(1969, carDto.getYear());
        assertEquals(4, carDto.getNumberOfSeats());

        validateTargetList(carDto.getManufacturingPlantDtos());
    }

    @Test
    void whenUsingQualifiedByName_thenConvertCarToCarDto() {
        Car car = createCarObject();

        CarDto carDto = QualifiedByNameCarMapper.INSTANCE.carToCarDto(car);

        assertEquals("Morris", carDto.getMake());
        assertEquals("Mini", carDto.getModel());
        assertEquals(1969, carDto.getYear());
        assertEquals(4, carDto.getNumberOfSeats());

        validateTargetList(carDto.getManufacturingPlantDtos());
    }


    private Car createCarObject() {
        Car car = new Car("Morris", "Mini", 1969, 4);
        car.setPlant1("Oxford");
        car.setPlant1Loc("United Kingdom");
        car.setPlant2("Swinden");
        car.setPlant2Loc("United Kingdom");
        return car;
    }

    private void validateTargetList(List<ManufacturingPlantDto> manufacturingPlantDtos) {
        assertEquals(2, manufacturingPlantDtos.size());
        manufacturingPlantDtos.forEach(plant -> {
            switch (plant.getPlantName()) {
                case "Oxford", "Swinden" -> assertEquals("United Kingdom", plant.getLocation());
                default -> fail("Unexpected plant name: " + plant.getPlantName());
            }
        });
    }
}
