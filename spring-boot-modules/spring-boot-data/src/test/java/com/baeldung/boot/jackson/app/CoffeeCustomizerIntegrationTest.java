package com.baeldung.boot.jackson.app;

import org.springframework.context.annotation.Import;

import com.baeldung.boot.jackson.config.CoffeeCustomizerConfig;

@Import(CoffeeCustomizerConfig.class)
public class CoffeeCustomizerIntegrationTest extends AbstractCoffeeIntegrationTest {
}
