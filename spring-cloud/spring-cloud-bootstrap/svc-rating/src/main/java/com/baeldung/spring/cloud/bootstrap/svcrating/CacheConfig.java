package com.baeldung.spring.cloud.bootstrap.svcrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class CacheConfig {

    @Autowired
    CacheProperties cacheProperties;
    
    @Bean(name="cacheConnectionFactory")
    @Qualifier("cacheJedisConnectionFactory")
    JedisConnectionFactory cacheConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(cacheProperties.hostName);
        factory.setPort(cacheProperties.port);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }
    
}
