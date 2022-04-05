package com.baeldung.boot.jackson.app;

import com.baeldung.boot.jackson.config.CoffeeCustomizerConfig;
import org.springframework.context.annotation.Import;

@Import(CoffeeCustomizerConfig.class)
public class CoffeeCustomizerIntegrationTest extends AbstractCoffeeIntegrationTest {
}
