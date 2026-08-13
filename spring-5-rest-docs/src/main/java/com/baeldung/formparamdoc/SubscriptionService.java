package com.baeldung.formparamdoc;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final AtomicLong sequence = new AtomicLong(1000);

    public SubscriptionResponse subscribe(SubscriptionForm form) {
        List<String> topics = form.getTopics() == null ? Collections.emptyList() : form.getTopics();

        return new SubscriptionResponse(sequence.incrementAndGet(), form.getEmail(), form.getFrequency(), topics, form.isMarketingAccepted());
    }
}