package com.baeldung.beaninjection.setterbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.model.Currency;
import com.baeldung.beaninjection.model.IndianRupee;

@Configuration
public class SetterBasedAppConfig {

    @Bean
    public Currency currency() {
        return new IndianRupee();
    }

}
