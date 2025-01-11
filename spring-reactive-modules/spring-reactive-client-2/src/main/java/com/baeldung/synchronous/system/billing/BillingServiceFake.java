package com.baeldung.synchronous.system.billing;

import org.springframework.stereotype.Service;

@Service
public class BillingServiceFake implements BillingService {

    @Override
    public Billing getBy(Long customerId) {
        return new Billing(customerId, 100.0d);
    }
}
