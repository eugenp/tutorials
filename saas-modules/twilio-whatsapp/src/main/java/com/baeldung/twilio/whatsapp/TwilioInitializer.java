package com.baeldung.twilio.whatsapp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;

@Component
@EnableConfigurationProperties(TwilioConfigurationProperties.class)
public class TwilioInitializer implements ApplicationRunner {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    public TwilioInitializer(TwilioConfigurationProperties twilioConfigurationProperties) {
        this.twilioConfigurationProperties = twilioConfigurationProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        String accountSid = twilioConfigurationProperties.getAccountSid();
        String authToken = twilioConfigurationProperties.getAuthToken();
        Twilio.init(accountSid, authToken);
    }

}