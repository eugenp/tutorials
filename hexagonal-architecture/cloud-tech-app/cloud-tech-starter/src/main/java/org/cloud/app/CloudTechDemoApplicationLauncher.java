package org.cloud.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CloudTechDemoApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(CloudTechDemoApplicationLauncher.class);
    }
}
