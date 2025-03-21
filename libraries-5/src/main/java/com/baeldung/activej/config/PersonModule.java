package com.baeldung.activej.config;

import com.baeldung.activej.controller.PersonController;
import com.baeldung.activej.repository.PersonRepository;
import com.baeldung.activej.service.PersonService;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class PersonModule extends AbstractModule {

    @Provides
    PersonController personController(PersonService personService) {
        return new PersonController(personService);
    }

    @Provides
    PersonService personService(PersonRepository personRepository) {
        return new PersonService(personRepository);
    }

    @Provides
    PersonRepository personRepository(DataSource dataSource) {
        return new PersonRepository(dataSource);
    }

    @Provides
    DataSource dataSource() {
        return new DataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return null;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return null;
            }

            @Override
            public PrintWriter getLogWriter() throws SQLException {
                return null;
            }

            @Override
            public void setLogWriter(PrintWriter out) throws SQLException {

            }

            @Override
            public void setLoginTimeout(int seconds) throws SQLException {

            }

            @Override
            public int getLoginTimeout() throws SQLException {
                return 0;
            }

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                return null;
            }
        };
    }
}
