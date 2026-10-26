package com.baeldung.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.baeldung.springai.advisor.ToolIterationLoggingAdvisor;
import com.baeldung.springai.service.WeatherService;
import com.baeldung.springai.tools.WeatherTools;

@Configuration
public class ChatClientConfiguration {

    @Bean
    @Primary
    ChatClient travelChatClient(ChatClient.Builder builder, WeatherTools weatherTools) {
        return builder
            .defaultTools(weatherTools)
            .defaultAdvisors(new ToolIterationLoggingAdvisor())
            .build();
    }

    @Bean
    ChatClient memoryTravelChatClient(ChatClient.Builder builder, WeatherTools weatherTools, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor
            .builder(chatMemory)
            .order(ToolCallingAdvisor.DEFAULT_ORDER + 100)
            .build();

        return builder
            .defaultTools(weatherTools)
            .defaultAdvisors(memoryAdvisor)
            .build();
    }

    @Bean
    ChatClient manualToolExecutionChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    ToolCallback currentWeather(WeatherService weatherService) {
        return FunctionToolCallback
            .builder("currentWeather",
                (WeatherRequest request) -> weatherService.getCurrentWeather(request.city()))
            .description("Get the current weather in a city")
            .inputType(WeatherRequest.class)
            .build();
    }

    record WeatherRequest(String city) {
    }
}
