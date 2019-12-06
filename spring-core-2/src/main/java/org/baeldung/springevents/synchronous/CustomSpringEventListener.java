package org.baeldung.springevents.synchronous;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

    @Override
    public void onApplicationEvent(final CustomSpringEvent event) {
        System.out.println("Received spring custom event - " + event.getMessage());
    }

}