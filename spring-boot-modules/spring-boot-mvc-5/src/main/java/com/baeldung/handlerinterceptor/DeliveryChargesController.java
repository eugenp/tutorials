package com.baeldung.handlerinterceptor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DeliveryChargesController {

    private final DeliveryChargeService deliveryChargeService;

    public DeliveryChargesController(DeliveryChargeService deliveryChargeService) {
        this.deliveryChargeService = deliveryChargeService;
    }

    @PostMapping("/delivery-charges/calculate")
    public double calculate(@RequestParam String postcode, HttpServletRequest request) {
        Boolean useV2 = (Boolean) request.getAttribute(DeliveryChargeInterceptor.USE_V2_ATTRIBUTE);
        if (Boolean.TRUE.equals(useV2)) {
            return deliveryChargeService.calculateV2(postcode);
        }
        return deliveryChargeService.calculateV1(postcode);
    }
}
