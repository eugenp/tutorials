package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.RedisConnectionDetailsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(RedisConnectionDetailsConfiguration.class)
@TestPropertySource(locations = {"classpath:connectiondetails/application-redis.properties"})
@ActiveProfiles("redis")
public class RedisCacheConnnectionDetailsIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConnnectionDetailsIntegrationTest.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void giveSecretVault_whenStoreInRedisCache_thenSuccess() {
        redisTemplate.opsForValue().set("City", "New York");
        assertEquals("New York", redisTemplate.opsForValue().get("City"));
    }
}
