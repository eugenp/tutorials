package com.baeldung.spring.jinq;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baeldung.spring.jinq.config.JinqProviderConfiguration;

@Configuration
@Import({ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JinqProviderConfiguration.class })
@ComponentScan(basePackages = "com.baeldung.spring.jinq.repositories")
@EntityScan("com.baeldung.spring.jinq.entities")
@EnableTransactionManagement
public class JinqApplication {
}
