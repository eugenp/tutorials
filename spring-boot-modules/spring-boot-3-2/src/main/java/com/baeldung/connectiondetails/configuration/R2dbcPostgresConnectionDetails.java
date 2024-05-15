package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcConnectionDetails;

public class R2dbcPostgresConnectionDetails implements R2dbcConnectionDetails {
    @Override
    public ConnectionFactoryOptions getConnectionFactoryOptions() {

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
          .option(ConnectionFactoryOptions.DRIVER, "postgresql")
          .option(ConnectionFactoryOptions.HOST, VaultAdapter.getSecret("r2dbc_postgres_host"))
          .option(ConnectionFactoryOptions.PORT, Integer.valueOf(VaultAdapter.getSecret("r2dbc_postgres_port")))
          .option(ConnectionFactoryOptions.USER, VaultAdapter.getSecret("r2dbc_postgres_user"))
          .option(ConnectionFactoryOptions.PASSWORD, VaultAdapter.getSecret("r2dbc_postgres_secret"))
          .option(ConnectionFactoryOptions.DATABASE, VaultAdapter.getSecret("r2dbc_postgres_database"))
          .build();

        return options;
    }
}
