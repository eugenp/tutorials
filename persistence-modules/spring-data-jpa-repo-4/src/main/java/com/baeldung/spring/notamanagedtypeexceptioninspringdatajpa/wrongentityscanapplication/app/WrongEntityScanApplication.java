package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages =
  "com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.repository")
public class WrongEntityScanApplication {

}
