package com.baeldung.spring.cloud.ribbon.retry.backoff;

import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.stereotype.Component;

@Component
@Profile("fixed-backoff")
class FixedBackoffRetryFactory extends RibbonLoadBalancedRetryFactory {

    public FixedBackoffRetryFactory(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public BackOffPolicy createBackOffPolicy(String service) {
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000);
        return fixedBackOffPolicy;
    }
}