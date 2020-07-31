package com.baeldung.propertiesvsyaml;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource getMemoryDataSource() {
        return DataSourceBuilder.create()
          .driverClassName("org.h2.Driver")
          .url("jdbc:h2:dev")
          .username("SA")
          .password("")
          .build();
    }
}