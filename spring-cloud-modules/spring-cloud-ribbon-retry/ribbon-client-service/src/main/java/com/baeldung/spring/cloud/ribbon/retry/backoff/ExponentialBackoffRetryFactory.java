package com.baeldung.spring.cloud.ribbon.retry.backoff;

import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.stereotype.Component;

@Component
@Profile("exponential-backoff")
class ExponentialBackoffRetryFactory extends RibbonLoadBalancedRetryFactory {

    public ExponentialBackoffRetryFactory(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public BackOffPolicy createBackOffPolicy(String service) {
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(1000);
        exponentialBackOffPolicy.setMultiplier(2);
        exponentialBackOffPolicy.setMaxInterval(10000);
        return exponentialBackOffPolicy;
    }
}