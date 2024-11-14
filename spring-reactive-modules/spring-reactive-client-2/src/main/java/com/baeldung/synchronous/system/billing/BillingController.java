package com.baeldung.synchronous.system.billing;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(BillingController.PATH_BILLING)
public class BillingController {

    public static final String PATH_BILLING = "/billing";
    public static final Duration SLEEP_DURATION = Duration.ofSeconds(4);

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/{id}")
    Mono<Billing> getBilling(@PathVariable("id") Long customerId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(SLEEP_DURATION.getSeconds());
        return Mono.just(billingService.getBy(customerId));
    }

}
