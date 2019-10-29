package org.baeldung.spring43.ctor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooRepositoryConfiguration {

    @Bean
    public FooRepository fooRepository() {
        return new FooRepository();
    }

}
