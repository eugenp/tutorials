package com.baeldung.springevents.synchronous;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GenericSpringEventListener implements ApplicationListener<GenericSpringAppEvent<String>> {

    // for testing
    private boolean hitEventHandler = false;

    @Override
    public void onApplicationEvent(@NonNull final GenericSpringAppEvent<String> event) {
        System.out.println("Received spring generic event - " + event.getWhat());
        hitEventHandler = true;
    }

    boolean isHitEventHandler() {
        return hitEventHandler;
    }
}