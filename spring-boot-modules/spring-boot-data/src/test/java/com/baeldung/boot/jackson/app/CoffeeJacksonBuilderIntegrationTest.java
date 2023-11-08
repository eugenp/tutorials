package com.baeldung.boot.jackson.app;

import org.springframework.context.annotation.Import;

import com.baeldung.boot.jackson.config.CoffeeJacksonBuilderConfig;

@Import(CoffeeJacksonBuilderConfig.class)
public class CoffeeJacksonBuilderIntegrationTest extends AbstractCoffeeIntegrationTest {
}
