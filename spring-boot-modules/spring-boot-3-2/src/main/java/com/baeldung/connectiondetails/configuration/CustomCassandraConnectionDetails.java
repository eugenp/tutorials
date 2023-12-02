package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.springframework.boot.autoconfigure.cassandra.CassandraConnectionDetails;

import java.util.List;

public class CustomCassandraConnectionDetails implements CassandraConnectionDetails {
    @Override
    public List<Node> getContactPoints() {
        Node node = new Node(
                VaultAdapter.getSecret("cassandra_host"),
                Integer.parseInt(VaultAdapter.getSecret("cassandra_port"))
        );
        return List.of(node);
    }

    @Override
    public String getUsername() {
        return VaultAdapter.getSecret("cassandra_user");
    }

    @Override
    public String getPassword() {
        return VaultAdapter.getSecret("cassandra_secret");
    }

    @Override
    public String getLocalDatacenter() {
        return "datacenter-1";
    }
}
