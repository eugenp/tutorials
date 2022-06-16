package com.baeldung.startwithoutdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@SpringBootTest(classes = StartWithoutDbApplication.class)
class StartWithoutDbIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenAutoConfigDisabled_whenStarting_thenNoAutoconfiguredBeansInContext() {
        context.getBean(DataSource.class);
    }


}
