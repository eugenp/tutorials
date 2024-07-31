package com.baeldung.flywaycallbacks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

public class ExampleFlywayCallback implements Callback {

    private final Log log = LogFactory.getLog(getClass());

    @Override
    public boolean supports(Event event, Context context) {
        return event == Event.AFTER_EACH_MIGRATE || event == Event.AFTER_MIGRATE || event == Event.BEFORE_EACH_MIGRATE || event == Event.BEFORE_MIGRATE;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        if (event == Event.AFTER_EACH_MIGRATE) {
            log.info("> afterEachMigrate");
        } else if (event == Event.AFTER_MIGRATE) {
            log.info("> afterMigrate");
        } else if (event == Event.BEFORE_EACH_MIGRATE) {
            log.info("> beforeEachMigrate");
        } else if (event == Event.BEFORE_MIGRATE) {
            log.info("> beforeMigrate");
        }
    }

    @Override
    public String getCallbackName() {
        return ExampleFlywayCallback.class.getSimpleName();
    }
}
