package com.baeldung.beaninjectiontypes;

import com.baeldung.lombok.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes")
class TestConfig {

    @Bean
    public Engine engine() {
        return new EngineImpl();
    }
}
