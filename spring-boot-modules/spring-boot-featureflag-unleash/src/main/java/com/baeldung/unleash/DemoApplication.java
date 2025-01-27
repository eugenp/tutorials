package com.baeldung.unleash;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws InterruptedException {
        String appName = "unleash-onboarding-java";
        String appInstanceID ="unleash-onboarding-instance";
        String appServerUrl = "http://localhost:4242/api/";
        String appToken = "<AddYourAPITokenHere>";

        SpringApplication.run(DemoApplication.class, args);
        UnleashConfig config = UnleashConfig.builder()
                .appName(appName)
                .instanceId(appInstanceID)
                .unleashAPI(appServerUrl)
                .apiKey(appToken)
                .build();

        Unleash unleash = new DefaultUnleash(config);
        while(true) {
            if (unleash.isEnabled("testDemoFeatureFlag")) {
                log.info("New feature is enabled!");
            } else {
                log.info("New feature is disabled!");
            }
            Thread.sleep(1000);
        }
    }

}
