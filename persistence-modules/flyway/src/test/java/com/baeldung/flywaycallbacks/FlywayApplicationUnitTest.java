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

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private DataSource dataSource;

    @Test
    public void migrateWithNoCallbacks() {
        logTestBoundary("migrateWithNoCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .load();
        flyway.migrate();
    }

    @Test
    public void migrateWithJavaCallbacks() {
        logTestBoundary("migrateWithJavaCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .callbacks(new ExampleFlywayCallback())
                .load();
        flyway.migrate();
    }

    @Test
    public void migrateWithSqlCallbacks() {
        logTestBoundary("migrateWithSqlCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration", "db/callbacks")
                .load();
        flyway.migrate();
    }

    @Test
    public void migrateWithSqlAndJavaCallbacks() {
        logTestBoundary("migrateWithSqlAndJavaCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration", "db/callbacks")
                .callbacks(new ExampleFlywayCallback())
                .load();
        flyway.migrate();
    }

    private void logTestBoundary(String testName) {
        System.out.println("\n");
        log.info("> " + testName);
    }
}
