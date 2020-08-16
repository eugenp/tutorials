package com.baeldung;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.Charset;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.google.common.io.Files;

import redis.embedded.RedisServer;

/**
 * Created by johnson on 3/9/17.
 */
public class RedissonConfigurationIntegrationTest {
    private static RedisServer redisServer;
    private static RedissonClient client;
    private static int port;

    @BeforeClass
    public static void setUp() throws IOException {

        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        redisServer = new RedisServer(port);
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
          .setAddress(String.format("redis://127.0.0.1:%s", port));

        client = Redisson.create(config);

        assert(client != null && client.getKeys().count() >= 0);
    }

    @Test
    public void givenJSONFileConfig_thenRedissonConnectToRedis() throws IOException {
        
        File configFile = new File(getClass().getClassLoader().getResource("singleNodeConfig.json").getFile());
        String configContent = Files.toString(configFile, Charset.defaultCharset()).replace("6379", String.valueOf(port));
        
        Config config = Config.fromJSON(configContent);
        client = Redisson.create(config);

        assert(client != null && client.getKeys().count() >= 0);
    }

    @Test
    public void givenYAMLFileConfig_thenRedissonConnectToRedis() throws IOException {
        
        File configFile = new File(getClass().getClassLoader().getResource("singleNodeConfig.yaml").getFile());
        String configContent = Files.toString(configFile, Charset.defaultCharset()).replace("6379", String.valueOf(port));
        
        Config config = Config.fromYAML(configContent);
        client = Redisson.create(config);

        assert(client != null && client.getKeys().count() >= 0);
    }
}