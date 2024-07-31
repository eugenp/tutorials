package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
public class SessionConfig {

}
