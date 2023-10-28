package com.baeldung.boot.jackson;

import com.baeldung.boot.jackson.config.CoffeeHttpConverterConfiguration;
import org.springframework.context.annotation.Import;

@Import(CoffeeHttpConverterConfiguration.class)
public class CoffeeHttpConverterIntegrationTest extends AbstractCoffeeIntegrationTest {
}
