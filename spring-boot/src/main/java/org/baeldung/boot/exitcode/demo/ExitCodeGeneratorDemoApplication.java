package org.baeldung.boot.exitcode.demo;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("DemoExitCodeGenerator")
@SpringBootApplication
public class ExitCodeGeneratorDemoApplication implements ExitCodeGenerator {
    public static void main(String[] args) {
        System.exit(SpringApplication
                .exit(SpringApplication.run(ExitCodeGeneratorDemoApplication.class, args)));
    }

    @Override
    public int getExitCode() {
        return 100;
    }
}
