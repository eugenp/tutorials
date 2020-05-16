package com.baeldung.spring.data.redis.delete;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.data.redis.config.RedisConfig;

import redis.embedded.RedisServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedisConfig.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class RedisFlushDatabaseIntegrationTest {

    private RedisServer redisServer;

    @Autowired
    @Qualifier("flushRedisTemplate")
    private RedisTemplate<String, String> flushRedisTemplate;

    @Before
    public void setup() throws IOException {
        redisServer = new RedisServer(6390);
        redisServer.start();
    }

    @After
    public void tearDown() {
        redisServer.stop();
    }

    @Test
    public void whenFlushDB_thenAllKeysInDatabaseAreCleared() {

        ValueOperations<String, String> simpleValues = flushRedisTemplate.opsForValue();
        String key = "key";
        String value = "value";
        simpleValues.set(key, value);
        assertThat(simpleValues.get(key)).isEqualTo(value);

        flushRedisTemplate.execute(new RedisCallback<Void>() {

            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return null;
            }
        });

        assertThat(simpleValues.get(key)).isNull();

    }
    
    @Test
    public void whenFlushAll_thenAllKeysInDatabasesAreCleared() {

        ValueOperations<String, String> simpleValues = flushRedisTemplate.opsForValue();
        String key = "key";
        String value = "value";
        simpleValues.set(key, value);
        assertThat(simpleValues.get(key)).isEqualTo(value);

        flushRedisTemplate.execute(new RedisCallback<Void>() {

            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });

        assertThat(simpleValues.get(key)).isNull();

    }
}