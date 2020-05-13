package com.baeldung.architecture.hexagonal.adaptor;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.BikeId;

public class BikeDataStoreAdaptorUnitTest {
    @Test
    public void testGetAllBikes() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        assertEquals(dataStoreAdaptor.getAllBikes()
            .size(), 6);
    }

    @Test
    public void testUpdateBike() {
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
    public void testGetBikeWhenBikeExists() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();

        List<Bike> bikes = dataStoreAdaptor.getAllBikes();

        Bike bikeOne = bikes.get(0);
        assertEquals(bikeOne, dataStoreAdaptor.getBike(bikeOne.getId()));
    }

    @Test
    public void testGetBikeWhenNoBikeMatchesId() {
        BikeDataStoreAdaptor dataStoreAdaptor = new BikeDataStoreAdaptor();
        assertNull(dataStoreAdaptor.getBike(new BikeId(23451)));
    }
}
