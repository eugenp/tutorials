package com.baeldung.hexagonalarchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.baeldung.hexagonalarchitecture.infrastructure.config.Config;

@SpringBootApplication
@Import(Config.class)
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
