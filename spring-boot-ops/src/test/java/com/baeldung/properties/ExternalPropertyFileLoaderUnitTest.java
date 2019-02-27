package com.baeldung.properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExternalPropertyFileLoaderUnitTest {

    @Test
    public void whenExternalisedPropertiesLoaded_thenReadValues() {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ExternalPropertyFileLoader.class).properties("spring.config.name:conf", "spring.config.location:file:src/main/resources/external/")
            .build()
            .run();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Assert.assertEquals(environment.getProperty("url"), "jdbc:postgresql://localhost:5432/");
        Assert.assertEquals(environment.getProperty("username"), "admin");
        Assert.assertEquals(environment.getProperty("password"), "root");
    }

}
