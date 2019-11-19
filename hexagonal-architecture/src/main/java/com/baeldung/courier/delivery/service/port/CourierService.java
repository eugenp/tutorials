package com.baeldung.courier.delivery.service.port;

import com.baeldung.courier.delivery.service.constants.Constants;
import com.baeldung.courier.delivery.service.core.domain.Courier;

public interface CourierService {

    int sendCourier(Courier courier);

    Constants.CourierStatus getCourierStatus(int trackingId);

    Courier getCourierDetails(int courierId);
}
