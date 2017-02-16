package com.baeldung.annotation.servletcomponentscan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.annotation.servletcomponentscan.components")
public class SpringBootPlainApp {

    public static void main(String[] args) {
    }

}
