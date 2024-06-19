package com.baeldung.spring.notamanagedtype.missedentityscan.fixed.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages =
  "com.baeldung.spring.notamanagedtype.missedentityscan.repository")
@EntityScan("com.baeldung.spring.notamanagedtype.missedentityscan.entity")
public class WrongEntityScanFixedApplication {

}
