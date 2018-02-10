package com.baeldung.methodoverridingoverloading.test;

import com.baeldung.methodoverridingoverloading.model.Car;
import com.baeldung.methodoverridingoverloading.model.Vehicle;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class MethodOverridingUnitTest {
    
    private static Vehicle vehicle;
    private static Car car;
    
    @BeforeClass
    public static void setUpVehicleInstance() {
        vehicle = new Vehicle();
    }
    
    @BeforeClass
    public static void setUpCarInstance() {
        car = new Car();
    }
    
    @Test
    public void givenVehicleInstance_whenCalledAccelerate_thenOneAssertion() {
        assertThat(vehicle.accelerate(100)).isEqualTo("The vehicle accelerates at : 100 MPH.");
    }
    
    @Test
    public void givenVehicleInstance_whenCalledRun_thenOneAssertion() {
        assertThat(vehicle.run()).isEqualTo("The vehicle is running.");
    }
    
    @Test
    public void givenVehicleInstance_whenCalledStop_thenOneAssertion() {
        assertThat(vehicle.stop()).isEqualTo("The vehicle has stopped.");
    }
    
    @Test
    public void givenCarInstance_whenCalledAccelerate_thenOneAssertion() {
        assertThat(car.accelerate(80)).isEqualTo("The car accelerates at the : 80 MPH.");
    }
    
    @Test
    public void givenCarInstance_whenCalledRun_thenOneAssertion() {
        assertThat(car.run()).isEqualTo("The vehicle is running.");
    }
    
    @Test
    public void givenCarInstance_whenCalledStop_thenOneAssertion() {
        assertThat(car.stop()).isEqualTo("The vehicle has stopped.");
    }
}
