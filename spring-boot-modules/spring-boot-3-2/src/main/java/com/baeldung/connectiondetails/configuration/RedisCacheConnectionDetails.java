package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails;

public class RedisCacheConnectionDetails implements RedisConnectionDetails {
    @Override
    public String getPassword() {
        return VaultAdapter.getSecret("redis_password");
    }

    @Override
    public Standalone getStandalone() {
        return new Standalone() {
            @Override
            public String getHost() {
                return VaultAdapter.getSecret("redis_host");
            }

            @Override
            public int getPort() {
                return Integer.parseInt(VaultAdapter.getSecret("redis_port"));
            }
        };
    }
}
