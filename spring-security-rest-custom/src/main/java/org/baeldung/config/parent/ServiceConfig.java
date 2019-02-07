package org.baeldung.config.parent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("org.baeldung.service")
// @ImportResource({ "classpath:prop.xml" })
@PropertySource("classpath:foo.properties")
public class ServiceConfig {

    public ServiceConfig() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

}