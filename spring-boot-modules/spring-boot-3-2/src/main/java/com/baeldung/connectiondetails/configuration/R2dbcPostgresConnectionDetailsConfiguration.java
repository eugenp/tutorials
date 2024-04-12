package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("r2dbc")
public class R2dbcPostgresConnectionDetailsConfiguration {
    @Bean
    @Primary
    public R2dbcConnectionDetails getR2dbcPostgresConnectionDetails() {
        return new R2dbcPostgresConnectionDetails();
    }
}
