package org.baeldung.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);
    public static int counter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Application started with option names : {}", args.getOptionNames());
        LOG.info("Increment counter");
        counter++;
    }
}