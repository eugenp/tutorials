package com.baeldung.typesofbeaninjection;

import com.baeldung.typesofbeaninjection.domain.Engine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.typesofbeaninjection")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("V8", 5);
    }
}
