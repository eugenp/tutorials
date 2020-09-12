package com.baeldung.booker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BookerApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(BookerApplication.class, args);
  }

}
