package com.baeldung.pointcutadvice.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class FooCreationEventListener implements ApplicationListener<FooCreationEvent> {

    private static Logger logger = Logger.getLogger(FooCreationEventListener.class.getName());

    @Override
    public void onApplicationEvent(FooCreationEvent event) {
        logger.info("Created foo instance: " + event.getSource().toString());
    }
}
