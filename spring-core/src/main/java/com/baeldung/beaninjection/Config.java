package com.baeldung.beaninjection;

import com.baeldung.beaninjection.domain.Data;
import com.baeldung.beaninjection.domain.PersonalData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class Config {

    @Bean
    public PersonalData personalData() {
        return new PersonalData("Stanley", "Kubrick");
    }

    @Bean
    public Data data() {
        return new Data("Clockwork Orange", "2h 17m");
    }

}
