package com.baeldung.architecture.hexagonal.adaptor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.Category;
import com.baeldung.architecture.hexagonal.domain.Size;

class BikeDataStoreAdaptorUnitTest {
    @Test
    void whenGetAllBikesIsCalled_thenAllBikesShouldBeReturned() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        assertEquals(dataStoreAdaptor.getAllBikes()
            .size(), 6);
    }

    @Test
    void whenBikeExists_thenUpdateBikeShouldSucceed() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        Collection<Bike> bikes = dataStoreAdaptor.getAllBikes();

        // verify bike one is not reserved
        Bike bikeOne = findBikeWithGivenIdInCollection(bikes, 1);
        assertFalse(bikeOne.isReserved());

        // update bike one to be reserved
        bikeOne.markReserved();
        dataStoreAdaptor.updateBike(bikeOne);

        // verify bike one is reserved
        bikeOne = findBikeWithGivenIdInCollection(bikes, 1);
        assertTrue(bikeOne.isReserved());
    }

    @Test
    void whenBikeIsNull_thenUpdateBikeShouldFail() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        try {
            dataStoreAdaptor.updateBike(null);

            fail("This shouldn't work");

        } catch (Throwable e) {
            assertEquals("Can't update data store with null bike.", e.getMessage());
        }
    }

    @Test
    void whenBikeDoesNotExist_thenUpdateBikeShouldFail() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        Bike bikeNotInDataStore = new Bike(144, false, Category.TANDEM, Size.TWELVE);

        try {
            dataStoreAdaptor.updateBike(bikeNotInDataStore);

            fail("This shouldn't work");

        } catch (Throwable e) {
            assertEquals("Bike with id 144 not found in data store.", e.getMessage());
        }
    }

    @Test
    void whenBikeExistsWithId_thenGetBikeShouldFindIt() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        Collection<Bike> bikes = dataStoreAdaptor.getAllBikes();

        Bike bikeOne = findBikeWithGivenIdInCollection(bikes, 1);
        assertEquals(bikeOne, dataStoreAdaptor.getBike(bikeOne.getId()));
    }

    @Test
    void whenNoBikeExistsWithId_thenGetBikeShouldNotFindOne() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        assertNull(dataStoreAdaptor.getBike(23451));
    }

    private static Bike findBikeWithGivenIdInCollection(Collection<Bike> bikes, int id) {
        for (Bike bike : bikes) {
            if (bike.getId() == id) {
                return bike;
            }
        }
        return null;
    }
}
