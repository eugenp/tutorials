package com.baeldung.spring.cloud.ribbon.retry.backoff;

import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.stereotype.Component;

@Component
@Profile("exponential-random-backoff")
class ExponentialRandomBackoffRetryFactory extends RibbonLoadBalancedRetryFactory {

    public ExponentialRandomBackoffRetryFactory(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public BackOffPolicy createBackOffPolicy(String service) {
        ExponentialRandomBackOffPolicy exponentialRandomBackOffPolicy = new ExponentialRandomBackOffPolicy();
        exponentialRandomBackOffPolicy.setInitialInterval(1000);
        exponentialRandomBackOffPolicy.setMultiplier(2);
        exponentialRandomBackOffPolicy.setMaxInterval(10000);
        return exponentialRandomBackOffPolicy;
    }
}