package com.baeldung.ratelimiting.bootstarterapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.baeldung.ratelimiting", exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class,
})
@EnableCaching
public class Bucket4jRateLimitingApp {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Bucket4jRateLimitingApp.class)
        .properties("spring.config.location=classpath:ratelimiting/application-bucket4j-starter.yml")
        .run(args);
  }
}
