package com.baeldung.config;

import com.baeldung.aop.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration @EnableAspectJAutoProxy public class LoggingAspectConfiguration {

    @Bean @Profile(Constants.SPRING_PROFILE_DEVELOPMENT) public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
