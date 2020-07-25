package com.baeldung.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventListenerExampleBean {
    private static final Logger LOG = LoggerFactory.getLogger(EventListenerExampleBean.class);

    public static int counter;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Increment counter");
        counter++;
    }
}
