package com.baeldung.beaninjection.config;

import com.baeldung.beaninjection.domain.Shield;
import com.baeldung.beaninjection.domain.Sword;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfiguration {


    @Bean
    public Sword sword(){
        return new Sword(10);
    }

    @Bean
    public Shield shield(){
        return new Shield(20);
    }

}
