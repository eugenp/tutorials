package com.baeldung.yauaa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Configuration
public class UserAgentAnalyzerConfiguration {

    private static final int CACHE_SIZE = 1000;

    @Bean
    public UserAgentAnalyzer userAgentAnalyzer() {
        return UserAgentAnalyzer
            .newBuilder()
            .withCache(CACHE_SIZE)
            .withField(UserAgent.DEVICE_CLASS)
            .build();
    }

}
