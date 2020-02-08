package com.baeldung.environmentpostprocessor.autoconfig;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.baeldung.environmentpostprocessor.calculator.GrossPriceCalculator;
import com.baeldung.environmentpostprocessor.calculator.NetPriceCalculator;
import com.baeldung.environmentpostprocessor.calculator.PriceCalculator;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class PriceCalculationAutoConfig {

    @Bean
    @ConditionalOnProperty(name = "com.baeldung.environmentpostprocessor.calculation.mode", havingValue = "NET")
    @ConditionalOnMissingBean
    public PriceCalculator getNetPriceCalculator() {
        return new NetPriceCalculator();
    }

    @Bean
    @ConditionalOnProperty(name = "com.baeldung.environmentpostprocessor.calculation.mode", havingValue = "GROSS")
    @ConditionalOnMissingBean
    public PriceCalculator getGrossPriceCalculator() {
        return new GrossPriceCalculator();
    }

}
