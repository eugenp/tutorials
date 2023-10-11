package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;

import java.util.List;

public class CustomElasticsearchConnectionDetails implements ElasticsearchConnectionDetails {
    @Override
    public List<Node> getNodes() {
        Node node1 = new Node(
                VaultAdapter.getSecret("elastic_host"),
                Integer.parseInt(VaultAdapter.getSecret("elastic_port1")),
                Node.Protocol.HTTP
        );
        return List.of(node1);
    }

    @Override
    public String getUsername() {
        return VaultAdapter.getSecret("elastic_user");
    }

    @Override
    public String getPassword() {
        return VaultAdapter.getSecret("elastic_secret");
    }

}
