package com.baeldung.si.security;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.security.channel.SecuredChannel;
import org.springframework.integration.security.channel.SecurityContextPropagationChannelInterceptor;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableIntegration
public class SecurityPubSubChannel {

    @Bean(name = "startPSChannel")
    @SecuredChannel(interceptor = "channelSecurityInterceptor", sendAccess = "ROLE_VIEWER")
    public PublishSubscribeChannel startChannel() {
        return new PublishSubscribeChannel(executor());
    }

    @ServiceActivator(inputChannel = "startPSChannel", outputChannel = "finalPSResult")
    @PreAuthorize("hasRole('ROLE_LOGGER')")
    public Message<?> changeMessageToRole(Message<?> message) {
        return buildNewMessage(getRoles(), message);
    }

    @ServiceActivator(inputChannel = "startPSChannel", outputChannel = "finalPSResult")
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public Message<?> changeMessageToUserName(Message<?> message) {
        return buildNewMessage(getUsername(), message);
    }

    @Bean(name = "finalPSResult")
    public DirectChannel finalPSResult() {
        return new DirectChannel();
    }

    @Bean
    @GlobalChannelInterceptor(patterns = { "startPSChannel", "endDirectChannel" })
    public ChannelInterceptor securityContextPropagationInterceptor() {
        return new SecurityContextPropagationChannelInterceptor();
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    public String getRoles() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(","));
    }

    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    public Message<String> buildNewMessage(String content, Message<?> message) {
        DefaultMessageBuilderFactory builderFactory = new DefaultMessageBuilderFactory();
        MessageBuilder<String> messageBuilder = builderFactory.withPayload(content);
        messageBuilder.copyHeaders(message.getHeaders());
        return messageBuilder.build();
    }

}
