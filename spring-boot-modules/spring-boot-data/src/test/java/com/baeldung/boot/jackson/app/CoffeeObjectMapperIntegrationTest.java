package com.baeldung.boot.jackson.app;

import org.springframework.context.annotation.Import;

import com.baeldung.boot.jackson.config.CoffeeObjectMapperConfig;

@Import(CoffeeObjectMapperConfig.class)
public class CoffeeObjectMapperIntegrationTest extends AbstractCoffeeIntegrationTest {
}
