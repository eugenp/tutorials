package com.baeldung.SpringCloudTaskFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.launcher.annotation.EnableTaskLauncher;

@SpringBootApplication
@EnableTaskLauncher
public class SpringCloudTaskSinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            SpringCloudTaskSinkApplication.class, args);
    }

}