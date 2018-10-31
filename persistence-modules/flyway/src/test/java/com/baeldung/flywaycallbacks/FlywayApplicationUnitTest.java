package com.baeldung.flywaycallbacks;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
public class FlywayApplicationUnitTest {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private DataSource dataSource;

    @Test
    public void migrateWithNoCallbacks() {
        logTestBoundary("migrateWithNoCallbacks");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration");
        flyway.migrate(); 
    }

    @Test
    public void migrateWithJavaCallbacks() {
        logTestBoundary("migrateWithJavaCallbacks");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration");
        flyway.setCallbacks(new ExampleFlywayCallback());
        flyway.migrate();
    }

    @Test
    public void migrateWithSqlCallbacks() {
        logTestBoundary("migrateWithSqlCallbacks");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration", "db/callbacks");
        flyway.migrate(); 
    }

    @Test
    public void migrateWithSqlAndJavaCallbacks() {
        logTestBoundary("migrateWithSqlAndJavaCallbacks");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration", "db/callbacks");
        flyway.setCallbacks(new ExampleFlywayCallback());
        flyway.migrate(); 
    }

    private void logTestBoundary(String testName) {
        System.out.println("\n");
        log.info("> " + testName);
    }
 }
