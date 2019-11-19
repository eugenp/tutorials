package com.baeldung.courier.delivery.service.adapter;

import com.baeldung.courier.delivery.service.constants.Constants.CourierStatus;
import com.baeldung.courier.delivery.service.core.domain.Courier;
import com.baeldung.courier.delivery.service.port.CourierDeliveryRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Repository
public class CourierDeliveryRepoImpl implements CourierDeliveryRepo {

    private Map<Integer, Courier> courierTracker = new HashMap<>();

    public int sendCourier(Courier courier) {
        int tracker = new Random().nextInt(10);
        courier.setCourierStatus(CourierStatus.INTRANSIT);
        courier.setTrackingId(tracker);
        courierTracker.put(tracker, courier);
        return tracker;
    }

    public CourierStatus getCourierStatus(int trackingId) {
        return courierTracker.get(trackingId).getCourierStatus();
    }

    public Courier getCourierDetails(int tracker) {
        return courierTracker.get(tracker);
    }
}
