package com.baeldung.quarkus.liquibase;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "liquibase", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public final class LiquibaseConfig {
    /**
     * master change log file
     */
    @ConfigItem
    public String changeLog;
}