package com.baeldung.multitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MultiTenantApplication {

    public static void main(String[] args) {
	    SpringApplication.run(MultiTenantApplication.class, args);
    }

}
