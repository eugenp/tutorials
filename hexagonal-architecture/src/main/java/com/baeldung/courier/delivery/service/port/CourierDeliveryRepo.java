package com.baeldung.courier.delivery.service.port;

import com.baeldung.courier.delivery.service.constants.Constants.CourierStatus;
import com.baeldung.courier.delivery.service.core.domain.Courier;

public interface CourierDeliveryRepo {

    int sendCourier(Courier courier);

    CourierStatus getCourierStatus(int trackingId);

    Courier getCourierDetails(int courierId);

}
