package com.baeldung.rss;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.security.RolesAllowed;

@SpringBootApplication(exclude = MySQLAutoconfiguration.class)
@ComponentScan(basePackages = "com.baeldung.rss")
public class RssApp {

    @RolesAllowed("*")
    public static void main(String[] args) {
        System.setProperty("security.basic.enabled", "false");
        SpringApplication.run(RssApp.class, args);
    }

}
