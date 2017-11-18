package org.baeldung.properties.parentchild.config;

import org.baeldung.properties.parentchild.ParentValueHolder;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ParentConfig2 {

    @Bean
    public ParentValueHolder parentValueHolder() {
        return new ParentValueHolder();
    }

    @Bean
    public static PropertyPlaceholderConfigurer configurer() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("parent.properties"));
        return ppc;
    }
}
