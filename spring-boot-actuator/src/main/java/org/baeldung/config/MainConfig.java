package org.baeldung.config;

import java.util.Collections;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class MainConfig {

    public MainConfig() {}

    @Bean
    public InfoContributor getInfoContributor() {
        return (infoBuilder) -> infoBuilder.withDetail("applicationInfo", Collections.singletonMap("ActiveUserCount", "10"));
    }
}
