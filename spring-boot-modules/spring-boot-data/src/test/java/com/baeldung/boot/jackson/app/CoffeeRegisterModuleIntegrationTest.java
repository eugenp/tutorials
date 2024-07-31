package com.baeldung.boot.jackson.app;

import org.springframework.context.annotation.Import;

import com.baeldung.boot.jackson.config.CoffeeRegisterModuleConfig;

@Import(CoffeeRegisterModuleConfig.class)
public class CoffeeRegisterModuleIntegrationTest extends AbstractCoffeeIntegrationTest {
}
