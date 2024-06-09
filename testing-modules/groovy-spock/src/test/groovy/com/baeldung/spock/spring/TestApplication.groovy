package com.baeldung.spock.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TestApplication {

    static void main(String[] args) {
        SpringApplication.run(TestApplication, args)
    }

}
