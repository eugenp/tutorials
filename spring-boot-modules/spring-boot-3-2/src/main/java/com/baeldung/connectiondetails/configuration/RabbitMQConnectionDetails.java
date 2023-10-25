package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;

public class RabbitMQConnectionDetails implements RabbitConnectionDetails {
    @Override
    public String getUsername() {
        return VaultAdapter.getSecret("rabbitmq_username");
    }

    @Override
    public String getPassword() {
        return VaultAdapter.getSecret("rabbitmq_password");
    }

    @Override
    public String getVirtualHost() {
        return "/";
    }

    @Override
    public List<Address> getAddresses() {
        return List.of(this.getFirstAddress());
    }

    @Override
    public Address getFirstAddress() {
        return new Address(VaultAdapter.getSecret("rabbitmq_host"),
                Integer.parseInt(VaultAdapter.getSecret("rabbitmq_port")));
    }
}
