package com.baeldung.clickhouse;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.clickhouse.ClickHouseContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    public ClickHouseContainer clickhouseContainer() {
        return new ClickHouseContainer("clickhouse/clickhouse-server:24.11");
    }

    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(ClickHouseContainer clickhouseContainer) {
        return registry -> {
            registry.add("spring.datasource.url", clickhouseContainer::getJdbcUrl);
            registry.add("spring.datasource.username", clickhouseContainer::getUsername);
            registry.add("spring.datasource.password", clickhouseContainer::getPassword);
            registry.add("spring.datasource.driver-class-name", clickhouseContainer::getDriverClassName);
        };
    }

}