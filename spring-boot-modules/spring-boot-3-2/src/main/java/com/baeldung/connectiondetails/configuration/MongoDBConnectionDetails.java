package com.baeldung.connectiondetails.configuration;


import com.baeldung.connectiondetails.adapter.VaultAdapter;
import com.mongodb.ConnectionString;
import org.springframework.boot.autoconfigure.mongo.MongoConnectionDetails;

public class MongoDBConnectionDetails implements MongoConnectionDetails {
    @Override
    public ConnectionString getConnectionString() {
        return new ConnectionString(VaultAdapter.getSecret("mongo_connection_string"));
    }
}

