package com.baeldung.dynamicendpoints;

import java.io.File;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.baeldung.dynamicendpoints.config.ReloadableProperties;

@SpringBootApplication
@EnableWebMvc
public class DynamicEndpointApp {
    public static void main(String[] args) {
        SpringApplication.run(DynamicEndpointApp.class, args);
    }


@Bean
@ConditionalOnProperty(name = "dynamic.endpoint.config.location", matchIfMissing = false)
public PropertiesConfiguration propertiesConfiguration(
    @Value("${dynamic.endpoint.config.location}") String path,
    @Value("${spring.properties.refreshDelay}") long refreshDelay) throws Exception {
    String filePath = path.substring("file:".length());
    PropertiesConfiguration configuration = new PropertiesConfiguration(new File(filePath).getCanonicalPath());
    FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
    fileChangedReloadingStrategy.setRefreshDelay(refreshDelay);
    configuration.setReloadingStrategy(fileChangedReloadingStrategy);
    return configuration;
}

    @Bean
    @ConditionalOnBean(PropertiesConfiguration.class)
    @Primary
    public Properties properties(PropertiesConfiguration propertiesConfiguration) throws Exception {
        ReloadableProperties properties = new ReloadableProperties(propertiesConfiguration);
        return properties;
    }
}
