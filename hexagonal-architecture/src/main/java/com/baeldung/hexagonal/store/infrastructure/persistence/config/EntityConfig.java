package com.baeldung.hexagonal.store.infrastructure.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.baeldung.hexagonal.store.core"})
public class EntityConfig {
}
