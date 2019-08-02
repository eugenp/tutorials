package com.baeldung.java.booleanoperators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XorUnitTest {

    @Test
    void givenDieselManualCar_whenXorOldSchool_ThenFalse() {
        Car car = new Car(true, true);
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselAutomaticCar_whenXorOldSchool_ThenTrue() {
        Car car = new Car(true, false);
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselManualCar_whenXorOldSchool_ThenTrue() {
        Car car = new Car(false, true);
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselAutomaticCar_whenXorOldSchool_ThenFalse() {
        Car car = new Car(false, false);
        boolean dieselXorManual = (car.isDiesel() && !car.isManual()) || (!car.isDiesel() && car.isManual());
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselManualCar_whenXor_ThenFalse() {
        Car car = new Car(true, true);
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isFalse();
    }

    @Test
    void givenDieselAutomaticCar_whenXor_ThenTrue() {
        Car car = new Car(true, false);
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselManualCar_whenXor_ThenTrue() {
        Car car = new Car(false, true);
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isTrue();
    }

    @Test
    void givenNonDieselAutomaticCar_whenXor_ThenFalse() {
        Car car = new Car(false, false);
        boolean dieselXorManual = car.isDiesel() ^ car.isManual();
        assertThat(dieselXorManual).isFalse();
    }
}
