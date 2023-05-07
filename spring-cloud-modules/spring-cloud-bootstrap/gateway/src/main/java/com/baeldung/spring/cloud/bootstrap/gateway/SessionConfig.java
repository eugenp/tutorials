package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
public class SessionConfig {

    @Autowired
    private Environment properties;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(properties.getProperty("spring.redis.host", "localhost"));
        redisConfiguration.setPort(properties.getProperty("spring.redis.port", Integer.TYPE, 6379));
        return new LettuceConnectionFactory(redisConfiguration);
    }
}
