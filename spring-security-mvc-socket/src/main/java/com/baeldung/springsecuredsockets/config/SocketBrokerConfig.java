package com.baeldung.springsecuredsockets.config;

import static com.baeldung.springsecuredsockets.Constants.SECURED_CHAT;
import static com.baeldung.springsecuredsockets.Constants.SECURED_CHAT_HISTORY;
import static com.baeldung.springsecuredsockets.Constants.SECURED_CHAT_ROOM;
import static com.baeldung.springsecuredsockets.Constants.SECURED_CHAT_SPECIFIC_USER;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("com.baeldung.springsecuredsockets.controllers")
public class SocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(SECURED_CHAT_HISTORY, SECURED_CHAT_SPECIFIC_USER);
        config.setApplicationDestinationPrefixes("/spring-security-mvc-socket");
        config.setUserDestinationPrefix("/secured/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(SECURED_CHAT_ROOM).withSockJS();
        registry.addEndpoint(SECURED_CHAT).withSockJS();
    }
}

