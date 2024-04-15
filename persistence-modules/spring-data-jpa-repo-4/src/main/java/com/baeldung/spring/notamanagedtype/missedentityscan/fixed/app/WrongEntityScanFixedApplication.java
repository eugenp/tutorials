package com.baeldung.spring.notamanagedtype.missedentityscan.fixed.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages =
  "com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.repository")
@EntityScan("com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.entity")
public class WrongEntityScanFixedApplication {

}
