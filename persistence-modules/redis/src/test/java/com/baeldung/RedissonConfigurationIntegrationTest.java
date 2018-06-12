package com.baeldung;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.embedded.RedisServer;

import java.io.File;
import java.io.IOException;

/**
 * Created by johnson on 3/9/17.
 */
public class RedissonConfigurationIntegrationTest {
    private static RedisServer redisServer;
    private static RedissonClient client;

    @BeforeClass
    public static void setUp() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterClass
    public static void destroy() {
        redisServer.stop();
        if (client != null) {
            client.shutdown();
        }
    }

    @Test
    public void givenJavaConfig_thenRedissonConnectToRedis() {
        Config config = new Config();
        config.useSingleServer()
          .setAddress("127.0.0.1:6379");

        client = Redisson.create(config);

        assert(client != null && client.getKeys().count() >= 0);
    }

    @Test
    public void givenJSONFileConfig_thenRedissonConnectToRedis() throws IOException {
        Config config = Config.fromJSON(
          new File(getClass().getClassLoader().getResource(
            "singleNodeConfig.json").getFile()));

        client = Redisson.create(config);

        assert(client != null && client.getKeys().count() >= 0);
    }

    @Test
    public void givenYAMLFileConfig_thenRedissonConnectToRedis() throws IOException {
        Config config = Config.fromYAML(
          new File(getClass().getClassLoader().getResource(
            "singleNodeConfig.yaml").getFile()));

        client = Redisson.create(config);

        assert(client != null && client.getKeys().count() >= 0);
    }
}