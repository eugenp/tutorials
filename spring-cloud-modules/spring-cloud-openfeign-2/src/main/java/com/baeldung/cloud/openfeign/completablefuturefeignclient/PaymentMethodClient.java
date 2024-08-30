package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "paymentClient", url = "http://payments-api.com")
public interface PaymentMethodClient {

    @RequestMapping(method = RequestMethod.GET, value = "/payments/methods")
    List<String> getAvailablePaymentMethods(@RequestParam(name = "site_id") String siteId);
}
