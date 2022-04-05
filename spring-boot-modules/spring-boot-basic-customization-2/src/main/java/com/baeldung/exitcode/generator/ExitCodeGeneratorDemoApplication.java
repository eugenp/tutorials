package com.baeldung.exitcode.generator;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExitCodeGeneratorDemoApplication implements ExitCodeGenerator {

    public static void main(String[] args) {
        System.exit(
            SpringApplication.exit(
                SpringApplication.run(ExitCodeGeneratorDemoApplication.class, args)
            )
        );
    }

    @Override
    public int getExitCode() {
        return 42;
    }
}
