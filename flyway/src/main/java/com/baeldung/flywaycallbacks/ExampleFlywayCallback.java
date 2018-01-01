package com.baeldung.flywaycallbacks;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.BaseFlywayCallback;

public class ExampleFlywayCallback extends BaseFlywayCallback {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public void afterEachMigrate(Connection connection, MigrationInfo info) {
        log.info("> afterEachMigrate");
    }

    @Override
    public void afterMigrate(Connection connection) {
        log.info("> afterMigrate");
    }

    @Override
    public void beforeEachMigrate(Connection connection, MigrationInfo info) {
        log.info("> beforeEachMigrate");
    }

    @Override
    public void beforeMigrate(Connection connection) {
        log.info("> beforeMigrate");
    }
}
