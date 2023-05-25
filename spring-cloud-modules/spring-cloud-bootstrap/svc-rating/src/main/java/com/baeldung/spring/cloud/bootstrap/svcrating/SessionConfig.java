package com.baeldung.spring.cloud.bootstrap.svcrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableRedisHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
    @Autowired
    Environment properties;
    
    @Bean
    @Primary
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(properties.getProperty("spring.redis.host","localhost"));
        factory.setPort(properties.getProperty("spring.redis.port", Integer.TYPE,6379));
        factory.afterPropertiesSet();
        factory.setUsePool(true);
        return factory;
    }
}
