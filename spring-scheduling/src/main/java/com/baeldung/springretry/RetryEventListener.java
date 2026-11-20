package com.baeldung.springretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.resilience.retry.MethodRetryEvent;
import org.springframework.stereotype.Component;

@Component
public class RetryEventListener {

    private static final Logger log = LoggerFactory.getLogger(RetryEventListener.class);

    @EventListener
    public void onRetryEvent(MethodRetryEvent event) {
        String methodName = event.getMethod()
            .getName();
        Throwable exception = event.getFailure();

        if (event.isRetryAborted()) {
            log.error("Retries exhausted for method '{}' after {} attempts. Final exception: {}", methodName, exception.getMessage());

        } else {
            log.warn("Retry failed for method '{}'. Exception: {}", methodName, exception.getMessage());
        }
    }
}
