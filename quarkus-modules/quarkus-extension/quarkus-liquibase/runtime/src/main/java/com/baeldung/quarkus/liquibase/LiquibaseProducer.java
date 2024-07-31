package com.baeldung.quarkus.liquibase;

import io.agroal.api.AgroalDataSource;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class LiquibaseProducer {

    @Inject
    AgroalDataSource dataSource;

    private LiquibaseConfig liquibaseConfig;

    @Produces
    public Liquibase produceLiquibase() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceAccessor classLoaderResourceAccessor = new ClassLoaderResourceAccessor(classLoader);
        Liquibase liquibase = new Liquibase(liquibaseConfig.changeLog, classLoaderResourceAccessor, new JdbcConnection(dataSource.getConnection()));
        return liquibase;
    }

    public void setLiquibaseConfig(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }
}