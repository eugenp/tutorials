package com.baeldung.java.booleanoperators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XorUnitTest {

    @Test
    void givenDieselManualCar_whenXorOldSchool_ThenFalse() {
        Car car = Car.dieselAndManualCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselAutomaticCar_whenXorOldSchool_ThenTrue() {
        Car car = Car.dieselAndAutomaticCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselManualCar_whenXorOldSchool_ThenTrue() {
        Car car = Car.oilAndManualCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselAutomaticCar_whenXorOldSchool_ThenFalse() {
        Car car = Car.oilAndAutomaticCar();
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselManualCar_whenXor_ThenFalse() {
        Car car = Car.dieselAndManualCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselAutomaticCar_whenXor_ThenTrue() {
        Car car = Car.dieselAndAutomaticCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselManualCar_whenXor_ThenTrue() {
        Car car = Car.oilAndManualCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselAutomaticCar_whenXor_ThenFalse() {
        Car car = Car.oilAndAutomaticCar();
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isFalse();
    }
}
