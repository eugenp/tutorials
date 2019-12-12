package com.baeldung.hexagonal.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication @EnableTransactionManagement public class ArtistDemoApplicationLauncher {
        public static void main(String[] args) {
                SpringApplication.run(ArtistDemoApplicationLauncher.class);
        }
}
