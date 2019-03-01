package com.baeldung;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class LettuceIntegrationLiveTest {

    private static Logger log = LoggerFactory.getLogger(LettuceIntegrationLiveTest.class);

    private static StatefulRedisConnection<String, String> redisConnection;

    private static RedisClient redisClient;

    @BeforeClass
    public static void setUp() {
        // Docker defaults to mapping redis port to 32768
        redisClient = RedisClient.create("redis://localhost:32768/");
        redisConnection = redisClient.connect();
    }

    @AfterClass
    public static void destroy() {
        redisConnection.close();
    }

    @Test
    public void givenAString_thenSaveItAsRedisStringsSync() {

        RedisCommands<String, String> syncCommands = redisConnection.sync();

        String key = "key";
        String value = "value";

        syncCommands.set(key, value);
        String response = syncCommands.get(key);

        Assert.assertEquals(value, response);
    }

    @Test
    public void givenValues_thenSaveAsRedisHashSync() {

        RedisCommands<String, String> syncCommands = redisConnection.sync();

        String recordName = "record1";
        String name = "FirstName";
        String value = "John";
        String surname = "LastName";
        String value1 = "Smith";

        syncCommands.hset(recordName, name, value);
        syncCommands.hset(recordName, surname, value1);
        Map<String, String> record = syncCommands.hgetall(recordName);

        Assert.assertEquals(record.get(name), value);
        Assert.assertEquals(record.get(surname), value1);
    }

    @Test
    public void givenAString_thenSaveItAsRedisStringsAsync() throws Exception {

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        String key = "key";
        String value = "value";

        asyncCommands.set(key, value);
        RedisFuture<String> redisFuture = asyncCommands.get(key);

        String response = redisFuture.get();

        Assert.assertEquals(value, response);
    }

    @Test
    public void givenValues_thenSaveAsRedisHashAsync() throws Exception {

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        String recordName = "record1";
        String name = "FirstName";
        String value = "John";
        String surname = "LastName";
        String value1 = "Smith";

        asyncCommands.hset(recordName, name, value);
        asyncCommands.hset(recordName, surname, value1);
        RedisFuture<Map<String, String>> redisFuture = asyncCommands.hgetall(recordName);

        Map<String, String> record = redisFuture.get();

        Assert.assertEquals(record.get(name), value);
        Assert.assertEquals(record.get(surname), value1);
    }

    @Test
    public void givenValues_thenSaveAsRedisListAsync() throws Exception {

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        String listName = "tasks";
        String firstTask = "firstTask";
        String secondTask = "secondTask";

        asyncCommands.del(listName);

        asyncCommands.lpush(listName, firstTask);
        asyncCommands.lpush(listName, secondTask);
        RedisFuture<String> redisFuture = asyncCommands.rpop(listName);

        String nextTask = redisFuture.get();

        Assert.assertEquals(firstTask, nextTask);

        asyncCommands.del(listName);

        asyncCommands.lpush(listName, firstTask);
        asyncCommands.lpush(listName, secondTask);

        redisFuture = asyncCommands.lpop(listName);

        nextTask = redisFuture.get();

        Assert.assertEquals(secondTask, nextTask);

    }

    @Test
    public void givenSetElements_thenSaveThemInRedisSetAsync() throws Exception {

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        String countries = "countries";

        String countryOne = "Spain";
        String countryTwo = "Ireland";
        String countryThree = "Ireland";

        asyncCommands.sadd(countries, countryOne);

        RedisFuture<Set<String>> countriesSetFuture = asyncCommands.smembers(countries);
        Assert.assertEquals(2, countriesSetFuture.get().size());

        asyncCommands.sadd(countries, countryTwo);
        countriesSetFuture = asyncCommands.smembers(countries);
        Assert.assertEquals(2, countriesSetFuture.get().size());

        asyncCommands.sadd(countries, countryThree);
        countriesSetFuture = asyncCommands.smembers(countries);
        Assert.assertEquals(2, countriesSetFuture.get().size());

        RedisFuture<Boolean> exists = asyncCommands.sismember(countries, countryThree);
        assertTrue(exists.get());
    }

    @Test
    public void givenARanking_thenSaveItInRedisSortedSetAsync() throws Exception {

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        String key = "sortedset";

        asyncCommands.zadd(key, 1, "one");
        asyncCommands.zadd(key, 4, "zero");
        asyncCommands.zadd(key, 2, "two");

        RedisFuture<List<String>> values = asyncCommands.zrevrange(key, 0, 3);
        Assert.assertEquals("zero", values.get().get(0));

        values = asyncCommands.zrange(key, 0, 3);
        Assert.assertEquals("one", values.get().get(0));
    }

    @Test
    public void givenMultipleOperationsThatNeedToBeExecutedAtomically_thenWrapThemInATransaction() throws Exception {

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        // Start a transaction
        asyncCommands.multi();

        // Add three sets to it, and save the future responses
        RedisFuture<String> result1 = asyncCommands.set("key1", "value1");
        RedisFuture<String> result2 = asyncCommands.set("key2", "value2");
        RedisFuture<String> result3 = asyncCommands.set("key3", "value3");

        // Execute it
        RedisFuture<TransactionResult> execResult = asyncCommands.exec();

        TransactionResult transactionResult = execResult.get();

        // Get the three results in the transaction return
        String firstResult = transactionResult.get(0);
        String secondResult = transactionResult.get(0);
        String thirdResult = transactionResult.get(0);

        // Our results are in both!
        assertTrue(firstResult.equals("OK"));
        assertTrue(secondResult.equals("OK"));
        assertTrue(thirdResult.equals("OK"));

        assertTrue(result1.get().equals("OK"));
        assertTrue(result2.get().equals("OK"));
        assertTrue(result3.get().equals("OK"));
    }

    @Test
    public void givenMultipleIndependentOperations_whenNetworkOptimizationIsImportant_thenFlushManually() throws Exception {

        int iterations = 50;

        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();

        asyncCommands.setAutoFlushCommands(false);

        List<RedisFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            futures.add(asyncCommands.set("key" + i, "value" + i));
        }

        asyncCommands.flushCommands();

        // Wait until all futures complete
        boolean result = LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[futures.size()]));

        asyncCommands.setAutoFlushCommands(true);

    }

    @Test
    public void givenPubSubChannel_whenMessage_thenMessageReceived() throws Exception {

        Listener listener = new Listener();
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        StatefulRedisPubSubConnection<String, String> pubconnection = redisClient.connectPubSub();
        connection.addListener(listener);

        RedisPubSubAsyncCommands<String, String> async = connection.async();
        async.subscribe("channel");

        RedisPubSubAsyncCommands<String, String> pubasync = pubconnection.async();
        RedisFuture<Long> result = pubasync.publish("channel", "hithere");

        // Need a long wait for publish to complete, depending on system.
        result.get(15, TimeUnit.SECONDS);
        assertTrue(listener.getMessage().equals("hithere"));

    }

    private static class Listener implements RedisPubSubListener<String, String> {

        private String message;

        String getMessage() {
            return message;
        }

        @Override
        public void message(String channel, String message) {
            log.debug("Got {} on {}", message, channel);
            this.message = message;
        }

        @Override
        public void message(String pattern, String channel, String message) {

        }

        @Override
        public void subscribed(String channel, long count) {
            log.debug("Subscribed to {}", channel);
        }

        @Override
        public void psubscribed(String pattern, long count) {

        }

        @Override
        public void unsubscribed(String channel, long count) {

        }

        @Override
        public void punsubscribed(String pattern, long count) {

        }
    }

}
