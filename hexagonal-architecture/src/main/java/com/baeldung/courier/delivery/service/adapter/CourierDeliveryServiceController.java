package com.baeldung.courier.delivery.service.adapter;

import com.baeldung.courier.delivery.service.constants.Constants.CourierStatus;
import com.baeldung.courier.delivery.service.core.domain.Courier;
import com.baeldung.courier.delivery.service.port.CourierDeliveryUI;
import com.baeldung.courier.delivery.service.port.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courierservice")
public class CourierDeliveryServiceController implements CourierDeliveryUI {

    @Autowired
    CourierService courierService;

    @Override
    public int sendCourier(@RequestBody Courier courier) {
        return courierService.sendCourier(courier);
    }

    @Override
    public CourierStatus getCourierStatus(@PathVariable int trackingId) {
        return courierService.getCourierStatus(trackingId);
    }

    @Override
    public Courier getCourierDetails(@PathVariable int courierId) {
        return courierService.getCourierDetails(courierId);
    }
}
