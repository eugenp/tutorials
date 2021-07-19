package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.userside.cli.CliUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexagonalArchitectureApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureApplication.class, args);
    }

    @Autowired
    CliUserController cliUserController;

    @Override
    public void run(String... args) throws Exception {
        cliUserController.createUser();
    }
}
