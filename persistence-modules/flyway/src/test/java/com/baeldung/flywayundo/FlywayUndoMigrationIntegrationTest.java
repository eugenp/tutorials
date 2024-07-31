package com.baeldung.flywayundo;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FlywayUndoTestConfig.class)
@SpringBootTest
public class FlywayUndoMigrationIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void givenMigrationsExist_whenApplyMigrations_migrationsAreSuccessful() {
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .schemas("undo")
            .locations("db/undo")
            .load();

        flyway.migrate();

        for (MigrationInfo info : flyway.info().all()) {
            assertThat(info.getState()).isEqualTo(MigrationState.SUCCESS);
        }
    }
}
