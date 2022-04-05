package com.baeldung.flywaycallbacks;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManualFlywayMigrationIntegrationTest {

    @Autowired
    private Flyway flyway;

    @Test
    public void skipAutomaticAndTriggerManualFlywayMigration() {

        assertAllMigrationsAre(MigrationState.PENDING);

        flyway.migrate();

        assertAllMigrationsAre(MigrationState.SUCCESS);
    }

    private void assertAllMigrationsAre(MigrationState expectedState) {
        for (MigrationInfo migrationInfo : flyway.info().all()) {
            assertThat(migrationInfo.getState()).isEqualTo(expectedState);
        }
    }
}
