package com.baeldung.staticmethods;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class CarUnitTest {

    @Test
    void givenCarInstance_whenGettingCarId_thenCorrectIdIsReturned() {
        Car car1 = new Car(1, "Karoq");
        assertThat(car1.getId()).isEqualTo(1);
    }

    @Test
    void givenCarInstance_whenGettingCarModel_thenCorrectModelReturned() {
        Car car1 = new Car(1, "karoq");
        assertThat(car1.getModel()).isEqualTo("karoq");
    }

    @Test
    void givenCarInstance_whenGettingCarModelCapitalized_thenCapitalizedModelReturned() {
        try (MockedStatic<StringUtils> utilities = Mockito.mockStatic(StringUtils.class)) {
            utilities.when(() -> StringUtils.capitalize("karoq")).thenReturn("Karoq");

            Car car1 = new Car(1, "karoq");
            assertThat(car1.getModelCapitalized()).isEqualTo("Karoq");
        }
    }

    @Test
    void givenCarInstance_whenGettingCarMake_thenCorrectMakeReturned() {
        Car car1 = new Car(1, "Karoq");
        assertThat(car1.getMake()).isEqualTo("Skoda");
    }

}
