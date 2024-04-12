package com.baeldung.boot.jackson.app;

import org.springframework.context.annotation.Import;

import com.baeldung.boot.jackson.config.CoffeeHttpConverterConfiguration;

@Import(CoffeeHttpConverterConfiguration.class)
public class CoffeeHttpConverterIntegrationTest extends AbstractCoffeeIntegrationTest {
}
