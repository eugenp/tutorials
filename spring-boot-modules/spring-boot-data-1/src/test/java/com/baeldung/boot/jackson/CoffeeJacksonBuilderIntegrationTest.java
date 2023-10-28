package com.baeldung.boot.jackson;

import com.baeldung.boot.jackson.config.CoffeeJacksonBuilderConfig;
import org.springframework.context.annotation.Import;

@Import(CoffeeJacksonBuilderConfig.class)
public class CoffeeJacksonBuilderIntegrationTest extends AbstractCoffeeIntegrationTest {
}
