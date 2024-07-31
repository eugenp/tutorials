package com.baeldung.persistence;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Setup implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setupDone;

    public Setup() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!setupDone) {
            System.out.println();
            setupDone = true;
        }
    }

}
