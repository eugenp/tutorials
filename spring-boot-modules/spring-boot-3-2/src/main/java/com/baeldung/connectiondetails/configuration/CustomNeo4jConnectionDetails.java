package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.springframework.boot.autoconfigure.neo4j.Neo4jConnectionDetails;

import java.net.URI;
import java.net.URISyntaxException;

public class CustomNeo4jConnectionDetails implements Neo4jConnectionDetails {
    @Override
    public URI getUri() {
        try {
            return new URI(VaultAdapter.getSecret("neo4j_uri"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public AuthToken getAuthToken() {
        return AuthTokens.basic("neo4j", VaultAdapter.getSecret("neo4j_secret"));
    }
}
