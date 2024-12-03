package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "paymentMethodClient", url = "http://localhost:8083")
public interface PaymentMethodClient {

    @RequestMapping(method = RequestMethod.GET, value = "/payment_methods")
    String getAvailablePaymentMethods(@RequestParam(name = "site_id") String siteId);
}
