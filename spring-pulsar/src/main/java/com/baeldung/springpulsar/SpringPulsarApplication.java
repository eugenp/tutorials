package com.baeldung.springpulsar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.pulsar.annotation.EnablePulsar;

@EnablePulsar
@SpringBootApplication
public class SpringPulsarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPulsarApplication.class, args);
    }

}
