package com.baeldung.mybatis.generatedid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = TestConfig.class)
class CarMapperUnitTest {

    @Autowired
    private CarMapper carMapper;

    @Test
    void givenCar_whenSaveUsingOptions_thenReturnId() {
        Car car = new Car();
        car.setModel("BMW");

        carMapper.saveUsingOptions(car);

        assertNotNull(car.getId());
    }

    @Test
    void givenCar_whenSaveUsingSelectKey_thenReturnId() {
        Car car = new Car();
        car.setModel("BMW");

        carMapper.saveUsingSelectKey(car);

        assertNotNull(car.getId());
    }

}