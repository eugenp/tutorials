package org.baeldung.startup;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListenerExample implements ApplicationListener<ContextRefreshedEvent> {

    public static int counter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        counter++;
    }
}
