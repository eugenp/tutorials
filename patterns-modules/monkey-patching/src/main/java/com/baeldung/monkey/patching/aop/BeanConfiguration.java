package com.baeldung.monkey.patching.aop;

import com.baeldung.monkey.patching.converter.MoneyConverter;
import com.baeldung.monkey.patching.converter.MoneyConverterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MoneyConverter moneyConverter() {
        return new MoneyConverterImpl();
    }
}
