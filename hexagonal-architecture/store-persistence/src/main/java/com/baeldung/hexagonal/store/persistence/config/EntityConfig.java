package com.baeldung.hexagonal.store.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.baeldung.hexogonal.store.core"})
public class EntityConfig {
}
