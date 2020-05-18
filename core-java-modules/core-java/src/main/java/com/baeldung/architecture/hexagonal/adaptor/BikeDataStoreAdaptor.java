package com.baeldung.architecture.hexagonal.adaptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.Category;
import com.baeldung.architecture.hexagonal.domain.Size;
import com.baeldung.architecture.hexagonal.port.BikeDataStorePort;

public class BikeDataStoreAdaptor implements BikeDataStorePort {

    private Map<Integer, Bike> bikeMap;

    public BikeDataStoreAdaptor() {
        bikeMap = new HashMap<>();
        bikeMap.put(1, new Bike(1, false, Category.ELECTRIC, Size.FIFTY));
        bikeMap.put(2, new Bike(2, false, Category.ELECTRIC, Size.FIFTYFOUR));
        bikeMap.put(3, new Bike(3, false, Category.KIDS, Size.SIXTEEN));
        bikeMap.put(4, new Bike(4, false, Category.KIDS, Size.TWELVE));
        bikeMap.put(5, new Bike(5, false, Category.TANDEM, Size.FIFTYTWO));
        bikeMap.put(6, new Bike(6, false, Category.TANDEM, Size.FOURTYSIX));
    }

    @Override
    public Collection<Bike> getAllBikes() {
        return bikeMap.values();
    }

    @Override
    public void updateBike(Bike bike) {
        assert bike != null : "Can't update data store with null bike.";
        assert bikeMap.containsKey(bike.getId()) : "Bike with id " + bike.getId() + " not found in data store.";

        bikeMap.replace(bike.getId(), bike);
    }

    @Override
    public Bike getBike(int bikeId) {
        return bikeMap.get(bikeId);
    }
}
