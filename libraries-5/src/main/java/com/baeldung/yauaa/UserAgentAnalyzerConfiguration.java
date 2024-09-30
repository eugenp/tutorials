package com.baeldung.yauaa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Configuration
public class UserAgentAnalyzerConfiguration {

    @Bean
    public UserAgentAnalyzer userAgentAnalyzer() {
        return UserAgentAnalyzer
            .newBuilder()
            .withCache(1000)
            .build();
    }

}
