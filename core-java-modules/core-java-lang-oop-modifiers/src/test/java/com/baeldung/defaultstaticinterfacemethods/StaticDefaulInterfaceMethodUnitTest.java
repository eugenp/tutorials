package com.baeldung.defaultstaticinterfacemethods;

import com.baeldung.defaultstaticinterfacemethods.model.Car;
import com.baeldung.defaultstaticinterfacemethods.model.Motorbike;
import com.baeldung.defaultstaticinterfacemethods.model.Vehicle;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class StaticDefaulInterfaceMethodUnitTest {

    private static Car car;
    private static Motorbike motorbike;

    @BeforeClass
    public static void setUpCarInstance() {
        car = new Car("BMW");
    }

    @BeforeClass
    public static void setUpMotorbikeInstance() {
        motorbike = new Motorbike("Yamaha");
    }

    @Test
    public void givenCarInstace_whenBrandisBMW_thenOneAssertion() {
        assertThat(car.getBrand()).isEqualTo("BMW");
    }

    @Test
    public void givenCarInstance_whenCallingSpeedUp_thenOneAssertion() {
        assertThat(car.speedUp()).isEqualTo("The car is speeding up.");
    }

    @Test
    public void givenCarInstance_whenCallingSlowDown_thenOneAssertion() {
        assertThat(car.slowDown()).isEqualTo("The car is slowing down.");
    }

    @Test
    public void givenCarInstance_whenCallingTurnAlarmOn_thenOneAssertion() {
        assertThat(car.turnAlarmOn()).isEqualTo("Turning the vehice alarm on.");
    }

    @Test
    public void givenCarInstance_whenCallingTurnAlarmOff_thenOneAssertion() {
        assertThat(car.turnAlarmOff()).isEqualTo("Turning the vehicle alarm off.");
    }

    @Test
    public void givenVehicleInterface_whenCallinggetHorsePower_thenOneAssertion() {
        assertThat(Vehicle.getHorsePower(2500, 480)).isEqualTo(228);
    }

    @Test
    public void givenMooorbikeInstace_whenBrandisYamaha_thenOneAssertion() {
        assertThat(motorbike.getBrand()).isEqualTo("Yamaha");
    }

    @Test
    public void givenMotorbikeInstance_whenCallingSpeedUp_thenOneAssertion() {
        assertThat(motorbike.speedUp()).isEqualTo("The motorbike is speeding up.");
    }

    @Test
    public void givenMotorbikeInstance_whenCallingSlowDown_thenOneAssertion() {
        assertThat(motorbike.slowDown()).isEqualTo("The motorbike is slowing down.");
    }

    @Test
    public void givenMotorbikeInstance_whenCallingTurnAlarmOn_thenOneAssertion() {
        assertThat(motorbike.turnAlarmOn()).isEqualTo("Turning the vehice alarm on.");
    }

    @Test
    public void givenMotorbikeInstance_whenCallingTurnAlarmOff_thenOneAssertion() {
        assertThat(motorbike.turnAlarmOff()).isEqualTo("Turning the vehicle alarm off.");
    }
}