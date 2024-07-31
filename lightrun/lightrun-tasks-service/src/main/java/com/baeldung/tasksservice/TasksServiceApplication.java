package com.baeldung.tasksservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TasksServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksServiceApplication.class, args);
    }

}
