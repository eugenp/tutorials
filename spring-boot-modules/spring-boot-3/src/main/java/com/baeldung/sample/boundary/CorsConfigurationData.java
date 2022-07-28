package com.baeldung.sample.boundary;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The properties from application.yml. You can specify them by the following snippet:
 *
 * <pre>
 * server:
 *   endpoints:
 *     api:
 *       v1: /api/v1
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "cors.allow")
@Data
public class CorsConfigurationData {

    private String[] origins = { "*" };
    private boolean credentials = false;

}
