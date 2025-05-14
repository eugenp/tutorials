package com.baeldung.listvsset.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public DataSourceWrapper dataSourceWrapper() {
        return new DataSourceWrapper();
    }

    @Bean
    public DatabaseUtil databaseUtil() {
        return new DatabaseUtil();
    }

    @Bean
    JsonUtils jsonUtils() {
        return new JsonUtils();
    }

}
