package com.baeldung.booleanoperators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XorUnitTest {

    @Test
    void givenDieselManualCar_whenXorOldSchool_thenFalse() {
        Car car = Car.dieselAndManualCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselAutomaticCar_whenXorOldSchool_thenTrue() {
        Car car = Car.dieselAndAutomaticCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselManualCar_whenXorOldSchool_thenTrue() {
        Car car = Car.oilAndManualCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselAutomaticCar_whenXorOldSchool_thenFalse() {
        Car car = Car.oilAndAutomaticCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselManualCar_whenXor_thenFalse() {
        Car car = Car.dieselAndManualCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselAutomaticCar_whenXor_thenTrue() {
        Car car = Car.dieselAndAutomaticCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselManualCar_whenXor_thenTrue() {
        Car car = Car.oilAndManualCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselAutomaticCar_whenXor_thenFalse() {
        Car car = Car.oilAndAutomaticCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isFalse();
    }
    
    @Test
    void givenNumbersOneAndThree_whenXor_thenTwo() {
        assertThat(1 ^ 3).isEqualTo(2);
    }
}
