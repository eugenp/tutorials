package org.baeldung.springquartz;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableScheduling
public class SpringQuartzBasicsApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringQuartzBasicsApp.class, args);
    }
}
