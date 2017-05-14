package org.baeldung.bean.config;

import org.baeldung.bean.injection.Owner;
import org.baeldung.bean.injection.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstructorBasedInjectionPetConfig {

    @Bean
    public Pet pet() {
        return new Pet(owner());
    }

    @Bean
    public Owner owner() {
        return new Owner();
    }

}
