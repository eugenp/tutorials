package com.baeldung.scheduling.shedlock;

import org.springframework.context.annotation.Configuration;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class SchedulerConfiguration {

}