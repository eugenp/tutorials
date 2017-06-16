package com.baeldung.spring.cloud.bootstrap.svcrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableConfigurationProperties({CacheProperties.class})
public class CacheConfig {

    @Autowired
    CacheProperties cacheProperties;
    
    @Bean(name="cacheConnectionFactory")
    @Qualifier("cacheJedisConnectionFactory")
    JedisConnectionFactory cacheConnectionFactory() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>Qualified Jedis Conn.Name.."+cacheProperties.hostName);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>Qualified Jedis Conn.Port.."+cacheProperties.port);
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(cacheProperties.hostName);
        factory.setPort(cacheProperties.port);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }
    
}
