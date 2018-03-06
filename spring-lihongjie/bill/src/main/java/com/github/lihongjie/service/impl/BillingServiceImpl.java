package com.github.lihongjie.service.impl;

import com.github.lihongjie.domain.Billing;
import com.github.lihongjie.model.BillingRequest;
import com.github.lihongjie.repository.BillingRepository;
import com.github.lihongjie.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BillingServiceImpl implements BillingService {

    private Logger logger = LoggerFactory.getLogger(BillingServiceImpl.class);

    @Autowired
    private BillingRepository billingRepository;

    @Override
    public Page<Billing> findAll(Pageable pageable) {

        return billingRepository.findAll(pageable);
    }

    @Override
    public Billing create(BillingRequest billingRequest) {

        LocalDateTime now = LocalDateTime.now();
        Billing billing = new Billing();
        billing.setName(billingRequest.getName());
        billing.setTotalPrice(billingRequest.getTotalPrice());
        billing.setDescription(billingRequest.getDescription());
        billing.setCreatedAt(now);
        billing.setCreatedBy(2L);
        billing.setLastEditAt(now);
        billing.setLastEditBy(2L);
        Billing newBilling = billingRepository.save(billing);
        logger.info("Successfully created the billing '{}' by '{}' at '{}'.", newBilling.getId(), 2L, now);
        return newBilling;
    }

    @Override
    public void deleteByBillingId(Long id) {

        billingRepository.delete(id);
        logger.info("Successfully deleted the billing '{}'.", id);
    }

    @Override
    public Billing updateBilling(BillingRequest billingRequest, Long billingId) {
        LocalDateTime now = LocalDateTime.now();
        Billing billing = billingRepository.findOne(billingId);
        billing.setName(billingRequest.getName());
        billing.setDescription(billingRequest.getDescription());
        billing.setTotalPrice(billingRequest.getTotalPrice());
        billing.setLastEditAt(now);
        billing.setLastEditBy(2L);
        Billing updatedBilling = billingRepository.save(billing);
        logger.info("Successfully updated the billing '{}' at '{}'.", billing.getId(), now);
        return updatedBilling;
    }

    @Override
    public Billing findBillingById(Long id) {

        return billingRepository.findOne(id);
    }
}
