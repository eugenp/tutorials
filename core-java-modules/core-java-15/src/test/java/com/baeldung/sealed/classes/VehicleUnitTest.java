package com.baeldung.sealed.classes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

public class VehicleUnitTest {

    private static Vehicle car;
    private static Vehicle truck;

    @BeforeAll
    public static void createInstances() {
        car = new Car(5, "VZ500DA");
        truck = new Truck(19000, "VZ600TA");
    }

    @Test
    public void givenCar_whenUsingReflectionAPI_thenSuperClassIsSealed() {
        Assertions.assertThat(car.getClass().isSealed()).isEqualTo(false);
        Assertions.assertThat(car.getClass().getSuperclass().isSealed()).isEqualTo(true);
        Assertions.assertThat(car.getClass().getSuperclass().permittedSubclasses())
                .contains(ClassDesc.of(car.getClass().getCanonicalName()));
    }

    @Test
    public void givenTruck_whenUsingReflectionAPI_thenSuperClassIsSealed() {
        Assertions.assertThat(truck.getClass().isSealed()).isEqualTo(false);
        Assertions.assertThat(truck.getClass().getSuperclass().isSealed()).isEqualTo(true);
        Assertions.assertThat(truck.getClass().getSuperclass().permittedSubclasses())
                .contains(ClassDesc.of(truck.getClass().getCanonicalName()));
    }

    @Test
    public void givenCar_whenGettingPropertyTraditionalWay_thenNumberOfSeatsPropertyIsReturned() {
        Assertions.assertThat(getPropertyTraditionalWay(car)).isEqualTo(5);
    }

    @Test
    public void givenCar_whenGettingPropertyViaPatternMatching_thenNumberOfSeatsPropertyIsReturned() {
        Assertions.assertThat(getPropertyViaPatternMatching(car)).isEqualTo(5);
    }

    @Test
    public void givenTruck_whenGettingPropertyTraditionalWay_thenLoadCapacityIsReturned() {
        Assertions.assertThat(getPropertyTraditionalWay(truck)).isEqualTo(19000);
    }

    @Test
    public void givenTruck_whenGettingPropertyViaPatternMatching_thenLoadCapacityIsReturned() {
        Assertions.assertThat(getPropertyViaPatternMatching(truck)).isEqualTo(19000);
    }

    private int getPropertyTraditionalWay(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return ((Car) vehicle).getNumberOfSeats();
        } else if (vehicle instanceof Truck) {
            return ((Truck) vehicle).getLoadCapacity();
        } else {
            throw new RuntimeException("Unknown instance of Vehicle");
        }
    }

    private int getPropertyViaPatternMatching(Vehicle vehicle) {
        if (vehicle instanceof Car car) {
            return car.getNumberOfSeats();
        } else if (vehicle instanceof Truck truck) {
            return truck.getLoadCapacity();
        } else {
            throw new RuntimeException("Unknown instance of Vehicle");
        }
    }

}
