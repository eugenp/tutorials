package com.baeldung.redis_scan;

import com.baeldung.redis_scan.client.RedisClient;
import org.junit.*;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NaiveApproachIntegrationTest {
    private static RedisServer redisServer;
    private static int port;
    private static RedisClient redisClient;

    @BeforeClass
    public static void setUp() throws IOException {

        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        redisServer = RedisServer.builder()
                .port(port)
                .setting("maxmemory 128M")
                .build();
    }

    @AfterClass
    public static void destroy() {
        if (redisServer.isActive()) {
            redisServer.stop();
            redisClient.destroyInstance();
        }
    }

    @Before
    public void init() {
        if (!redisServer.isActive()) {
            redisServer.start();
        }
        redisClient = RedisClient.getInstance("127.0.0.1", port);
    }

    @After
    public void flushAll() {
        redisClient.flushAll();
    }

    @Test
    public void testKeys() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("balls:cricket", "160");
        keyValues.put("balls:football", "450");
        keyValues.put("balls:volleyball", "270");
        redisClient.mset(keyValues);
        Set<String> readKeys = redisClient.keys("ball*");
        Assert.assertEquals(keyValues.size(), readKeys.size());

    }

    @Test
    public void testSmembers() {
        HashSet<String> setMembers = new HashSet<>();
        setMembers.add("cricket_160");
        setMembers.add("football_450");
        setMembers.add("volleyball_270");
        redisClient.sadd("balls", setMembers.toArray(new String[setMembers.size()]));
        Set<String> readSetMembers = redisClient.smembers("balls");
        Assert.assertEquals(setMembers.size(), readSetMembers.size());
    }

    @Test
    public void testHgetAll() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("balls:cricket", "160");
        keyValues.put("balls:football", "450");
        keyValues.put("balls:volleyball", "270");
        redisClient.hmset("balls", keyValues);
        Map<String, String> readHash = redisClient.hgetAll("balls");
        Assert.assertEquals(keyValues.size(), readHash.size());
    }

    @Test
    public void testZRange() {
        HashMap<String, Double> scoreMembers = new HashMap<>();
        scoreMembers.put("cricket", (double) 160);
        scoreMembers.put("football", (double) 450);
        scoreMembers.put("volleyball", (double) 270);
        redisClient.zadd("balls", scoreMembers);
        List<String> readSetMembers = redisClient.zrange("balls", 0, -1);

        Assert.assertEquals(readSetMembers.size(), scoreMembers.size());
    }

}
