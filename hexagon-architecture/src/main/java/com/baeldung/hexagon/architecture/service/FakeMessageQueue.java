package com.baeldung.hexagon.architecture.service;

import com.baeldung.hexagon.architecture.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeMessageQueue {
    private static final Logger LOG = LoggerFactory.getLogger(FakeMessageQueue.class);

    private String url;

    public FakeMessageQueue(String url) {
        this.url = url;
    }

    public void message(Country country) {
        LOG.info("Submitted interest for country name={}", country);
    }
}
