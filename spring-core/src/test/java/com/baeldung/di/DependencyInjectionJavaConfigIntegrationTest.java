package com.baeldung.di;

import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.configuration.ApplicationContextTestDependencyInjection;
import com.baeldung.dependency.AuditService;
import com.baeldung.dependency.DataSource;
import com.baeldung.dependency.DatabaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContextTestDependencyInjection.class)
public class DependencyInjectionJavaConfigIntegrationTest {

    @Inject
    DatabaseService databaseService;
    @Inject
    DataSource dataSource;
    @Inject
    AuditService auditService;

    @Test
    public void whenDatabaseServiceIsInjected_thenIsNotNull() {
        Assert.assertNotNull(databaseService);
    }

    @Test
    public void whenDataSourceIsInjected_thenIsNotNull() {
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void whenAuditServiceIsInjected_thenIsNotNull() {
        Assert.assertNotNull(auditService);
    }

}
