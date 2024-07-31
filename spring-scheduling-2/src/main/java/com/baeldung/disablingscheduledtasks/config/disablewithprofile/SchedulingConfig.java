package com.baeldung.disablingscheduledtasks.config.disablewithprofile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("!integrationTest")
public class SchedulingConfig {

}