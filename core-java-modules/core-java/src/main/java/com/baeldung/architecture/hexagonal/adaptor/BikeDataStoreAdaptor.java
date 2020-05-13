package com.baeldung.architecture.hexagonal.adaptor;

import java.util.Arrays;
import java.util.List;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.BikeId;
import com.baeldung.architecture.hexagonal.domain.Category;
import com.baeldung.architecture.hexagonal.domain.Size;
import com.baeldung.architecture.hexagonal.port.BikeDataStorePort;

public class BikeDataStoreAdaptor implements BikeDataStorePort {

    private final Bike[] allBikes = new Bike[] { new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY), new Bike(new BikeId(2), false, Category.ELECTRIC, Size.FIFTYFOUR), new Bike(new BikeId(3), false, Category.KIDS, Size.SIXTEEN),
            new Bike(new BikeId(4), false, Category.KIDS, Size.TWELVE), new Bike(new BikeId(5), false, Category.TANDEM, Size.FIFTYTWO), new Bike(new BikeId(6), false, Category.TANDEM, Size.FOURTYSIX), };

    @Override
    public List<Bike> getAllBikes() {
        return Arrays.asList(allBikes);
    }

    @Override
    public void updateBike(Bike bike) {
        for (int i = 0; i < allBikes.length; i++) {
            if (allBikes[i].getId() == bike.getId()) {
                allBikes[i] = new Bike(bike.getId(), bike.isReserved(), bike.getCategory(), bike.getSize());
            }
        }
    }

    public Bike getBike(BikeId bikeId) {
        for (int i = 0; i < allBikes.length; i++) {
            if (allBikes[i].getId() == bikeId) {
                return allBikes[i];
            }
        }
        return null;
    }
}
