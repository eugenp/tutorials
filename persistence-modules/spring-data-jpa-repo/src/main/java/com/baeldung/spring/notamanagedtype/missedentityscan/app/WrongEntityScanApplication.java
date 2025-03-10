package com.baeldung.spring.notamanagedtype.missedentityscan.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages =
  "com.baeldung.spring.notamanagedtype.missedentityscan.repository")
public class WrongEntityScanApplication {

}
