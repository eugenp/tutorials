package com.baeldung.di;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependency.DataSource;
import com.baeldung.dependency.DatabaseServiceWithoutAnnotations;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dependencyinjectiontypes-context.xml" })
public class DependencyInjectionXmlConfigIntegrationTest implements ApplicationContextAware {

    DatabaseServiceWithoutAnnotations databaseService;
    DataSource dataSource;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        databaseService = applicationContext.getBean(DatabaseServiceWithoutAnnotations.class);
        dataSource = applicationContext.getBean(DataSource.class);
    }

    @Test
    public void whenDatabaseServiceIsInjected_thenIsNotNull() {
        Assert.assertNotNull(databaseService);
    }

    @Test
    public void whenDataSourceIsInjected_thenIsNotNull() {
        Assert.assertNotNull(dataSource);
    }

}
