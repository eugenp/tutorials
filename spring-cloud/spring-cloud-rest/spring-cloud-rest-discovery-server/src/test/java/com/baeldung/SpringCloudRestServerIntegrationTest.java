package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCloudRestServerIntegrationTest {
    @Test
    public void contextLoads() {
    }
    
    @EnableRedisHttpSession
    @Configuration
    static class Config {
        
        @Bean
        @SuppressWarnings("unchecked")
        public RedisSerializer<Object> defaultRedisSerializer() {
            return Mockito.mock(RedisSerializer.class);
        }

        @Bean
        public RedisConnectionFactory connectionFactory() {
            
            RedisConnectionFactory factory = Mockito.mock(RedisConnectionFactory.class);
            RedisConnection connection = Mockito.mock(RedisConnection.class);
            Mockito.when(factory.getConnection()).thenReturn(connection);

            return factory;
        }
    }
}