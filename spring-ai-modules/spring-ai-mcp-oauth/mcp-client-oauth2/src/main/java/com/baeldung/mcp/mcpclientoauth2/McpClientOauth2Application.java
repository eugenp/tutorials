package com.baeldung.mcp.mcpclientoauth2;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import io.modelcontextprotocol.client.McpSyncClient;

@SpringBootApplication
public class McpClientOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(McpClientOauth2Application.class, args);
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpClients) {
        return chatClientBuilder.defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpClients))
            .build();
    }

    @Bean
    WebClient.Builder webClientBuilder(McpSyncClientExchangeFilterFunction filterFunction) {
        return WebClient.builder()
            .apply(filterFunction.configuration());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.anyRequest()
                .permitAll())
            .oauth2Client(Customizer.withDefaults())
            .csrf(CsrfConfigurer::disable)
            .build();
    }

}
