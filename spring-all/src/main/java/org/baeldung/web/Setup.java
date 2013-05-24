package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Setup implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setupDone;

    @Autowired
    IServiceA serviceA;

    @Autowired
    ServiceB serviceB;

    public Setup() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!setupDone) {
            //
        }
    }

}
