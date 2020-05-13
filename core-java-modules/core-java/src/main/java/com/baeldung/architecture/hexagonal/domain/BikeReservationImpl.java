package com.baeldung.architecture.hexagonal.domain;

import java.util.List;

import com.baeldung.architecture.hexagonal.adaptor.BikeDataStoreAdaptor;
import com.baeldung.architecture.hexagonal.port.BikeReservationPort;

public class BikeReservationImpl implements BikeReservationPort {
    BikeDataStoreAdaptor dataStore = new BikeDataStoreAdaptor();

    public Bike processRequest(BikeReservationRequest bikeReservationRequest) throws NoAvailableBikesException {
        assert bikeReservationRequest != null : "Bike reservation request can not be null";

        List<Bike> allBikes = dataStore.getAllBikes();
        assert allBikes != null && allBikes.size() > 0 : "Bike data store must contain bikes";

        Bike bike = findMatchingBike(bikeReservationRequest);
        if (bike == null) {
            throw new NoAvailableBikesException(bikeReservationRequest);
        }

        reserveBike(bike);

        return bike;
    }

    private Bike findMatchingBike(BikeReservationRequest bikeReservationRequest) {
        for (Bike bike : dataStore.getAllBikes()) {
            if (bike.isMatchingBike(bikeReservationRequest)) {
                return bike;
            }
        }

        return null;
    }

    private void reserveBike(Bike bike) {
        bike.markReserved();
        dataStore.updateBike(bike);
    }
}
