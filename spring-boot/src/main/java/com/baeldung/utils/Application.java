package com.baeldung.utils;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@SpringBootApplication(exclude=MySQLAutoconfiguration.class)
@ComponentScan(basePackages="com.baeldung.utils")
public class Application {

	@RolesAllowed("*")
	public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
	}

}
