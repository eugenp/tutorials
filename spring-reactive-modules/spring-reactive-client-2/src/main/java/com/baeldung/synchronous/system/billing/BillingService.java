package com.baeldung.synchronous.system.billing;

public interface BillingService {

    Billing getBy(Long customerId);
}
