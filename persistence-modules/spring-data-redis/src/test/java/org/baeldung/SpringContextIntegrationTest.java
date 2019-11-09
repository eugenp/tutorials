package org.baeldung;

import com.baeldung.spring.data.redis.config.RedisConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServerBuilder;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = BEFORE_CLASS)
@ContextConfiguration(classes = RedisConfig.class)
public class SpringContextIntegrationTest {

    private static redis.embedded.RedisServer redisServer;

    @BeforeClass
    public static void startRedisServer() {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 256M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() {
        redisServer.stop();
    }

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
