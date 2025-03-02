package com.baeldung.list;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.list.entity.Car;
import com.baeldung.list.entity.CarDto;

public class SourcePropsToListElementsUnitTest {
    @Test
    void whenUseMappingExpression_thenConvertCarToCarDto() {
        Car car = createCarObject();

        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        assertEquals("Morris", carDto.getMake());
        assertEquals("Mini", carDto.getModel());
        assertEquals(1969, carDto.getYear());
        assertEquals(4, carDto.getNumberOfSeats());
        assertEquals(2, carDto.getManufacturingPlantDtos().size());
    }

    @Test
    void whenUsingDecorator_thenConvertCarToCarDto() {
        Car car = createCarObject();

        CarDto carDto = CustomCarMapper.INSTANCE.carToCarDto(car);

        assertEquals("Morris", carDto.getMake());
        assertEquals("Mini", carDto.getModel());
        assertEquals(1969, carDto.getYear());
        assertEquals(4, carDto.getNumberOfSeats());
        assertEquals(2, carDto.getManufacturingPlantDtos().size());
    }


    private Car createCarObject() {
        Car car = new Car("Morris", "Mini", 1969, 4);
        car.setPlant1("Oxford");
        car.setPlant1Loc("United Kingdom");
        car.setPlant2("Swinden");
        car.setPlant2Loc("United Kingdom");
        return car;
    }

}
