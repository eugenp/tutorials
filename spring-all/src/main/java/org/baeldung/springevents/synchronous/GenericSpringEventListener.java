package org.baeldung.springevents.synchronous;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GenericSpringEventListener implements ApplicationListener<GenericSpringAppEvent<String>> {

    @Override
    public void onApplicationEvent(@NonNull final GenericSpringAppEvent<String> event) {
        System.out.println("Received spring generic event - " + event.getWhat());
    }

}