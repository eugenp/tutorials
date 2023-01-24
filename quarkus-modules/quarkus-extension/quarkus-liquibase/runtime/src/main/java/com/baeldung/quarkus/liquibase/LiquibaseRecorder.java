package com.baeldung.quarkus.liquibase;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.arc.runtime.BeanContainerListener;
import io.quarkus.runtime.annotations.Recorder;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;

@Recorder
public class LiquibaseRecorder {

    public BeanContainerListener setLiquibaseConfig(LiquibaseConfig liquibaseConfig) {
        return beanContainer -> {
            LiquibaseProducer producer = beanContainer.instance(LiquibaseProducer.class);
            producer.setLiquibaseConfig(liquibaseConfig);
        };
    }

    public void migrate(BeanContainer container) throws LiquibaseException {
        Liquibase liquibase = container.instance(Liquibase.class);
        liquibase.update(new Contexts());
    }

}
