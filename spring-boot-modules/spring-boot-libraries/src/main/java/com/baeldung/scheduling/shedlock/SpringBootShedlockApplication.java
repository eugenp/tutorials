package com.baeldung.scheduling.shedlock;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class SpringBootShedlockApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootShedlockApplication.class, args);
    }
}
