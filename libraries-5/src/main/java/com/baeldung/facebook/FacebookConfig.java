package com.baeldung.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacebookConfig {

    @Value("${facebook.access.token}")
    private String accessToken;

    @Value("${facebook.app.secret}")
    private String appSecret;

    @Bean
    public FacebookClient facebookClient() {
        return new DefaultFacebookClient(accessToken, appSecret, Version.LATEST);
    }
}