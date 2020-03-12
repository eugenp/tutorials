package com.baeldung.redis_scan;

import com.baeldung.redis_scan.client.RedisClient;
import com.baeldung.redis_scan.iterator.RedisIterator;
import com.baeldung.redis_scan.strategy.ScanStrategy;
import com.baeldung.redis_scan.strategy.impl.Hscan;
import com.baeldung.redis_scan.strategy.impl.Scan;
import com.baeldung.redis_scan.strategy.impl.Sscan;
import com.baeldung.redis_scan.strategy.impl.Zscan;
import org.junit.*;
import redis.clients.jedis.Tuple;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;


public class ScanStrategyIntegrationTest {

    private static RedisServer redisServer;
    private static int port;
    private static RedisClient redisClient;

    @BeforeClass
    public static void setUp() throws IOException {

        // Take an available port
        ServerSocket s = new ServerSocket(0);
        String ip = "127.0.0.1";
        port = s.getLocalPort();
        s.close();

        redisServer = new RedisServer(port);
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
    public void testScanStrategy() {
        HashMap<String, String> keyValues = new HashMap<String, String>();
        keyValues.put("balls:cricket", "160");
        keyValues.put("balls:football", "450");
        keyValues.put("balls:volleyball", "270");
        redisClient.mset(keyValues);

        ScanStrategy<String> scanStrategy = new Scan();
        int iterationCount = 2;
        RedisIterator iterator = redisClient.iterator(iterationCount, "ball*", scanStrategy);
        List<String> results = new LinkedList<String>();
        while (iterator.hasNext()) {
            results.addAll(iterator.next());
        }
        Assert.assertEquals(keyValues.size(), results.size());
    }

    @Test
    public void testSscanStrategy() {
        HashSet<String> setMembers = new HashSet<String>();
        setMembers.add("cricket_160");
        setMembers.add("football_450");
        setMembers.add("volleyball_270");
        redisClient.sadd("balls", setMembers.toArray(new String[setMembers.size()]));

        Sscan scanStrategy = new Sscan("balls");
        int iterationCount = 2;
        RedisIterator iterator = redisClient.iterator(iterationCount, "*", scanStrategy);
        List<String> results = new LinkedList<String>();
        while (iterator.hasNext()) {
            results.addAll(iterator.next());
        }
        Assert.assertEquals(setMembers.size(), results.size());
    }

    @Test
    public void testHscanStrategy() {
        HashMap<String, String> hash = new HashMap<String, String>();
        hash.put("cricket", "160");
        hash.put("football", "450");
        hash.put("volleyball", "270");
        redisClient.hmset("balls", hash);

        Hscan scanStrategy = new Hscan("balls");
        int iterationCount = 2;
        RedisIterator iterator = redisClient.iterator(iterationCount, "*", scanStrategy);
        List<Map.Entry<String, String>> results = new LinkedList<Map.Entry<String, String>>();
        while (iterator.hasNext()) {
            results.addAll(iterator.next());
        }
        Assert.assertEquals(hash.size(), results.size());
    }

    @Test
    public void testZscanStrategy() {
        HashMap<String, Double> memberScores = new HashMap<String, Double>();
        memberScores.put("cricket", (double) 160);
        memberScores.put("football", (double) 450);
        memberScores.put("volleyball", (double) 270);
        redisClient.zadd("balls", memberScores);

        Zscan scanStrategy = new Zscan("balls");
        int iterationCount = 2;
        RedisIterator iterator = redisClient.iterator(iterationCount, "*", scanStrategy);
        List<Tuple> results = new LinkedList<Tuple>();
        while (iterator.hasNext()) {
            results.addAll(iterator.next());
        }
        Assert.assertEquals(memberScores.size(), results.size());
    }

}
