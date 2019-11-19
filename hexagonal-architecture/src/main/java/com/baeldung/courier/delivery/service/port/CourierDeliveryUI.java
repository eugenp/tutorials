package com.baeldung.courier.delivery.service.port;

import com.baeldung.courier.delivery.service.constants.Constants.*;
import com.baeldung.courier.delivery.service.core.domain.Courier;
import org.springframework.web.bind.annotation.*;

public interface CourierDeliveryUI {

    @PostMapping("/sendCourier")
    public int sendCourier(@RequestBody Courier courier);

    @GetMapping("/getCourierStatus/{trackingId}")
    public CourierStatus getCourierStatus(@PathVariable int trackingId);

    @GetMapping("/getCourierDetails/{courierId}")
    public @ResponseBody Courier getCourierDetails(@PathVariable int courierId);
}
