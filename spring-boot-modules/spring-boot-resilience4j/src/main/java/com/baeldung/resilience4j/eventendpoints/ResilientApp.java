package com.baeldung.resilience4j.eventendpoints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class ResilientApp {

  public static void main(String[] args) {
    SpringApplication.run(ResilientApp.class, args);
  }
}
