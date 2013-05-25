package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

// @Component
public class Setup implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setupDone;

    @Autowired
    ServiceA serviceA;

    @Autowired
    IServiceB serviceB;

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
