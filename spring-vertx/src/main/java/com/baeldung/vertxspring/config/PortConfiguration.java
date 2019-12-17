package com.baeldung.vertxspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class PortConfiguration {

    private static final int DEFAULT_PORT = 8069;


    @Profile("default")
    @Bean
    public Integer defaultPort() {
        return DEFAULT_PORT;
    }

    @Profile("test")
    @Bean
    public Integer randomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();

        } catch (IOException e) {
            return DEFAULT_PORT;
        }
    }
}
