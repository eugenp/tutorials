package com.baeldung.springai.vectorstore.oracle;

import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;
import org.testcontainers.oracle.OracleContainer;

class OracleDatabaseContainerConnectionDetailsFactory
    extends ContainerConnectionDetailsFactory<OracleContainer, JdbcConnectionDetails> {

    @Override
    protected JdbcConnectionDetails getContainerConnectionDetails(ContainerConnectionSource<OracleContainer> source) {
        return new OracleDatabaseContainerConnectionDetails(source);
    }

    private static final class OracleDatabaseContainerConnectionDetails
        extends ContainerConnectionDetails<OracleContainer> implements JdbcConnectionDetails {

        OracleDatabaseContainerConnectionDetails(ContainerConnectionSource<OracleContainer> source) {
            super(source);
        }

        @Override
        public String getUsername() {
            return getContainer().getUsername();
        }

        @Override
        public String getPassword() {
            return getContainer().getPassword();
        }

        @Override
        public String getJdbcUrl() {
            return getContainer().getJdbcUrl();
        }

    }

}