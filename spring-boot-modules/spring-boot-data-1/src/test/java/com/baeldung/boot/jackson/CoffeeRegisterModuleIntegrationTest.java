package com.baeldung.boot.jackson;

import com.baeldung.boot.jackson.config.CoffeeRegisterModuleConfig;
import org.springframework.context.annotation.Import;

@Import(CoffeeRegisterModuleConfig.class)
public class CoffeeRegisterModuleIntegrationTest extends AbstractCoffeeIntegrationTest {
}
