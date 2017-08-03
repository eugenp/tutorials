package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependency.AuditService;
import com.baeldung.dependency.DataSource;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.dependency" })
public class ApplicationContextTestDependencyInjection {

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1/");
        dataSource.setUsername("username");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    public AuditService auditService() {
        AuditService auditService = new AuditService("INFO");
        return auditService;
    }
}
