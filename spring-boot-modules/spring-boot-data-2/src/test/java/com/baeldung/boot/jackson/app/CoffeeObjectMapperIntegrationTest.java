package com.baeldung.boot.jackson.app;

import com.baeldung.boot.jackson.config.CoffeeObjectMapperConfig;
import org.springframework.context.annotation.Import;

@Import(CoffeeObjectMapperConfig.class)
public class CoffeeObjectMapperIntegrationTest extends AbstractCoffeeIntegrationTest {
}
