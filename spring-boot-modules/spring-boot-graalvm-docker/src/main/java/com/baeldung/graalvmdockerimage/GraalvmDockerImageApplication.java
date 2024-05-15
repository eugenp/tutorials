package com.baeldung.graalvmdockerimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GraalvmDockerImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraalvmDockerImageApplication.class, args);
    }

}

@RestController
class HelloController {
    
    @GetMapping
    public String hello() {
        return "Hello GraalVM";
    }
}
