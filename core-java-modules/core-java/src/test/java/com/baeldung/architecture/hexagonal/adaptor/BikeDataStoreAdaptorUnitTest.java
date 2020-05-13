package com.baeldung.architecture.hexagonal.adaptor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.BikeId;
import com.baeldung.architecture.hexagonal.domain.Category;
import com.baeldung.architecture.hexagonal.domain.Size;

class BikeDataStoreAdaptorUnitTest {
    @Test
    void whenGetAllBikesIsCalled_thenGetAllBikes() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        assertEquals(dataStoreAdaptor.getAllBikes()
            .size(), 6);
    }

    @Test
    void whenBikeDoesExist_thenUpdateBikeShouldSucceed() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        List<Bike> bikes = dataStoreAdaptor.getAllBikes();

        // update bike one to be reserved
        Bike bikeOne = bikes.get(0);
        assertFalse(bikeOne.isReserved());

        bikeOne.markReserved();
        dataStoreAdaptor.updateBike(bikeOne);

        bikes = dataStoreAdaptor.getAllBikes();
        assertTrue(bikes.get(0)
            .isReserved());
    }

    @Test
    void whenBikeDoesNotExist_thenUpdateBikeShouldFail() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        Bike bikeNotInDataStore = new Bike(new BikeId(144), false, Category.TANDEM, Size.TWELVE);

        try {
            dataStoreAdaptor.updateBike(bikeNotInDataStore);

            fail("This shouldn't work");

        } catch (Throwable e) {
            assertEquals("Bike with id BikeId{id=144} was not found in data store.", e.getMessage());
        }
    }

    @Test
    void whenBikeExists_thenGetSingleBike() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        List<Bike> bikes = dataStoreAdaptor.getAllBikes();

        Bike bikeOne = bikes.get(0);
        assertEquals(bikeOne, dataStoreAdaptor.getBike(bikeOne.getId()));
    }

    @Test
    void whenNoBikeExists_thenGetSingleBike() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        assertNull(dataStoreAdaptor.getBike(new BikeId(23451)));
    }
}
