package com.baeldung.defaultistaticinterfacemethods.test;

import com.baeldung.defaultstaticinterfacemethods.model.Car;
import com.baeldung.defaultstaticinterfacemethods.model.Motorbike;
import com.baeldung.defaultstaticinterfacemethods.model.Vehicle;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StaticDefaulInterfaceMethodTest {
    
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
        assertEquals("BMW", car.getBrand());
    }
    
    @Test
    public void givenCarInstance_whenCallingSpeedUp_thenOneAssertion() {
        assertEquals("The car is speeding up.", car.speedUp());
    }
    
    @Test
    public void givenCarInstance_whenCallingSlowDown_thenOneAssertion() {
        assertEquals("The car is slowing down.", car.slowDown());
    }
    
    @Test
    public void givenCarInstance_whenCallingTurnAlarmOn_thenOneAssertion() {
        assertEquals("Turning the vehice alarm on.", car.turnAlarmOn());
    }
    
    @Test
    public void givenCarInstance_whenCallingTurnAlarmOff_thenOneAssertion() {
        assertEquals("Turning the vehicle alarm off.", car.turnAlarmOff());
    }
    
    @Test
    public void givenVehicleInterface_whenCallinggetHorsePower_thenOneAssertion() {
        assertEquals(228, Vehicle.getHorsePower(2500, 480));
    }
    
    @Test
    public void givenMooorbikeInstace_whenBrandisYamaha_thenOneAssertion() {
        assertEquals("Yamaha", motorbike.getBrand());
    }
    
    @Test
    public void givenMotorbikeInstance_whenCallingSpeedUp_thenOneAssertion() {
        assertEquals("The motorbike is speeding up.", motorbike.speedUp());
    }
    
    @Test
    public void givenMotorbikeInstance_whenCallingSlowDown_thenOneAssertion() {
        assertEquals("The motorbike is slowing down.", motorbike.slowDown());
    }
    
    @Test
    public void givenMotorbikeInstance_whenCallingTurnAlarmOn_thenOneAssertion() {
        assertEquals("Turning the vehice alarm on.", motorbike.turnAlarmOn());
    }
    
    @Test
    public void givenMotorbikeInstance_whenCallingTurnAlarmOff_thenOneAssertion() {
        assertEquals("Turning the vehicle alarm off.", motorbike.turnAlarmOff());
    }
}
