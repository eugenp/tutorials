package com.baeldung.annotation.servletcomponentscan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@SpringBootApplication(exclude=MySQLAutoconfiguration.class)
@ComponentScan(basePackages = "com.baeldung.annotation.servletcomponentscan.components")
class SpringBootPlainApp {

    public static void main(String[] args) {
    }

}
