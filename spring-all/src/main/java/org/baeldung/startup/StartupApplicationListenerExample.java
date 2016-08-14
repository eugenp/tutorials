package org.baeldung.startup;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListenerExample implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(StartupApplicationListenerExample.class);

    public static int counter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Increment counter");
        counter++;
    }
}
