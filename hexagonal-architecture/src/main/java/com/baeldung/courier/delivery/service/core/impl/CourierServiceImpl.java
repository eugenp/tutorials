package com.baeldung.courier.delivery.service.core.impl;

import com.baeldung.courier.delivery.service.constants.Constants.CourierStatus;
import com.baeldung.courier.delivery.service.core.domain.Courier;
import com.baeldung.courier.delivery.service.port.CourierDeliveryRepo;
import com.baeldung.courier.delivery.service.port.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Component
public class CourierServiceImpl implements CourierService {

    @Autowired
    CourierDeliveryRepo courierDeliveryRepo;

    @Override
    public int sendCourier(Courier courier) {
        return courierDeliveryRepo.sendCourier(courier);
    }

    @Override
    public CourierStatus getCourierStatus(int trackingId) {
        return courierDeliveryRepo.getCourierStatus(trackingId);
    }

    @Override
    public @ResponseBody Courier getCourierDetails(int courierId) {
        return courierDeliveryRepo.getCourierDetails(courierId);
    }
}
