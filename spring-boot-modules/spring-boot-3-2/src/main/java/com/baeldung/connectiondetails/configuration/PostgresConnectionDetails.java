package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;

public class PostgresConnectionDetails implements JdbcConnectionDetails {
    @Override
    public String getUsername() {
        return VaultAdapter.getSecret("postgres_user_key");
    }

    @Override
    public String getPassword() {
        return VaultAdapter.getSecret("postgres_secret_key");
    }

    @Override
    public String getJdbcUrl() {
        return VaultAdapter.getSecret("postgres_jdbc_url");
    }
}
