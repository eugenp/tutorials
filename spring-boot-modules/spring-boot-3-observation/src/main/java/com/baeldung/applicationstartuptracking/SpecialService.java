package com.baeldung.applicationstartuptracking;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Service;

@Service
public class SpecialService {

    private ApplicationStartup applicationStartup;

    public SpecialService(ApplicationContext context) {
        this.applicationStartup = ((ConfigurableApplicationContext) context).getApplicationStartup();
        init();
    }

    public void init() {
        StartupStep startupStep1 = this.applicationStartup.start("com.baeldung.special.service");
        startupStep1.tag("init", "connect to databases");
        // some long-running initialization
        startupStep1.end();

        StartupStep startupStep2 = this.applicationStartup.start("com.baeldung.special.service");
        startupStep2.tag("init", "connect to AI agent");
        // more long-running initialization
        startupStep2.end();
    }
}
