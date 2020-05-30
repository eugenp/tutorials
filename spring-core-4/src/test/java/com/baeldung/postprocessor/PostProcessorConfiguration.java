package com.baeldung.postprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorConfiguration {

    @Bean
    public GlobalEventBus eventBus() {
        return GlobalEventBus.getInstance();
    }

    @Bean
    public GuavaEventBusBeanPostProcessor eventBusBeanPostProcessor() {
        return new GuavaEventBusBeanPostProcessor();
    }

    @Bean
    public StockTradePublisher stockTradePublisher() {
        return new StockTradePublisher();
    }
}
