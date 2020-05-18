package com.baeldung.architecture.hexagonal.domain;

import java.util.Collection;

import com.baeldung.architecture.hexagonal.adaptor.BikeDataStoreAdaptor;
import com.baeldung.architecture.hexagonal.port.BikeReservationPort;

public class BikeReservationImpl implements BikeReservationPort {
    private BikeDataStoreAdaptor dataStore = new BikeDataStoreAdaptor();

    public Bike processReservationRequest(Category category, Size size) {
        assert (category != null && size != null) : "category and size can not be null";

        Bike bike = findAvailableBike(category, size);
        reserveBike(bike);

        return bike;
    }

    private void reserveBike(Bike bike) {
        bike.markReserved();
        dataStore.updateBike(bike);
    }

    private Bike findAvailableBike(Category category, Size size) {
        Collection<Bike> allBikes = dataStore.getAllBikes();
        assert allBikes != null && allBikes.size() > 0 : "Bike data store must contain bikes";

        Bike matchingBike = null;
        for (Bike bike : allBikes) {
            if (bike.isAvailable() && bike.getCategory() == category && bike.getSize() == size) {
                matchingBike = bike;
                break;
            }
        }

        assert matchingBike != null : "No bikes were available in category " + category + " and size " + size;

        return matchingBike;
    }
}
