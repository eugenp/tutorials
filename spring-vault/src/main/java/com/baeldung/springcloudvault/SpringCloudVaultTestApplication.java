package com.baeldung.springcloudvault;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

@SpringBootApplication
public class SpringCloudVaultTestApplication {
    public static void main(String... args) {
        SpringApplication.run(SpringCloudVaultTestApplication.class, args);
    }

    @Bean
    CommandLineRunner listSecrets(Environment env) {
        return args -> {
            var foo = env.getProperty("foo");
            Assert.notNull(foo, "foo must have a value");
            System.out.println("foo=" + foo);
        };
    }
}
