package com.baeldung.disableautoconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJPA.class)
public class SpringDataJPAIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void givenAutoConfigDisabled_whenStarting_thenNoAutoconfiguredBeansInContext() {
        context.getBean(DataSource.class);
    }

}
