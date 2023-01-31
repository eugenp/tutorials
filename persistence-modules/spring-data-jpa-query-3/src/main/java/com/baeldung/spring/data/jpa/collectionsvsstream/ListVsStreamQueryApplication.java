package com.baeldung.spring.data.jpa.collectionsvsstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ListVsStreamQueryApplication {

  @Autowired private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(ListVsStreamQueryApplication.class, args);
  }
}
