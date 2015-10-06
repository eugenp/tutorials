package org.baeldung.springquartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@EnableScheduling
public class SpringQuartzApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringQuartzApp.class)
                .showBanner(false).run(args);
    }
}
