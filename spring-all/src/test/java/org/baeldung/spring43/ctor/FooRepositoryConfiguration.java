package org.baeldung.spring43.ctor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FooRepositoryConfiguration {

    @Bean
    public FooRepository fooRepository() {
        return new FooRepository();
    }

}
