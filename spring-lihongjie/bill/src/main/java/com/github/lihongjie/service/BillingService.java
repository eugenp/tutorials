package com.github.lihongjie.service;

import com.github.lihongjie.domain.Billing;
import com.github.lihongjie.model.BillingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillingService {

    Page<Billing> findAll(Pageable pageable);

    Billing create(BillingRequest billingRequest);

    void deleteByBillingId(Long id);

    Billing updateBilling(BillingRequest billingRequest, Long id);

    Billing findBillingById(Long id);
}
