package com.baeldung.testcontainers.podman;


import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

class RedisLiveTest {

    @Test
    void whenSettingValue_thenCanGetItBack() {
        try (RedisContainer redis = new RedisContainer("redis:7-alpine").withExposedPorts(6379)) {
            redis.start();

            String host = redis.getHost();
            int port = redis.getFirstMappedPort();

            try (Jedis jedis = new Jedis(host, port)) {
                jedis.set("greeting", "hello");
                String value = jedis.get("greeting");
                Assertions.assertEquals("hello", value);
            }
        }
    }
}
