/**
 * These test cases have dependency with docker because they pull the docker images from docker hub
 * and run the container. So, please make sure to install docker before running the tests.
 * For the image details please look into the docker-compose files under resources/connectiondetails/docker
 **/
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
public class RedisCacheConnnectionDetailsLiveTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConnnectionDetailsLiveTest.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void giveSecretVault_whenStoreInRedisCache_thenSuccess() {
        redisTemplate.opsForValue().set("City", "New York");
        assertEquals("New York", redisTemplate.opsForValue().get("City"));
    }
}
