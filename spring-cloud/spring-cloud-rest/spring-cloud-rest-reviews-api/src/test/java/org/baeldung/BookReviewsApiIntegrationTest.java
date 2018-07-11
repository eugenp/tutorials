package org.baeldung;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.embedded.RedisServer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookReviewsApiIntegrationTest {
    
    private static RedisServer redisServer;
    private static int port;

    @BeforeClass
    public static void setUp() throws IOException {

        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterClass
    public static void destroy() {
        redisServer.stop();
    }

    @Test
    public void contextLoads() {
    }

}
