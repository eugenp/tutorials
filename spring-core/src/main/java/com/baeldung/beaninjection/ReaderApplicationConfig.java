package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.beaninjection" })
public class ReaderApplicationConfig {

    @Bean
    public FtpReader exampleDAO() {
        return new FtpReader("localhost", 21);
    }
}
