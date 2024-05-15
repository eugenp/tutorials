package com.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.baeldung.persistence.dao", "com.baeldung.persistence.service" })
@ImportResource({ "classpath:hibernate6Config.xml" })
public class PersistenceXmlConfig {

}