package com.baeldung.startwithoutdb;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = StartWithoutDbApplication.class)
class StartWithoutDbIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenAutoConfigDisabled_whenStarting_thenNoAutoconfiguredBeansInContext() {
        context.getBean(DataSource.class);
    }


}
