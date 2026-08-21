package com.baeldung.springai.toolsearchtool;

import org.springaicommunity.tool.search.ToolSearchToolCallAdvisor;
import org.springaicommunity.tool.search.ToolSearcher;
import org.springaicommunity.tool.searcher.RegexToolSearcher;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TravelAssistantConfig {

    @Bean
    ToolSearcher toolSearcher() {
        return new RegexToolSearcher();
    }

    @Bean
    ToolSearchToolCallAdvisor toolSearchToolCallAdvisor(ToolSearcher toolSearcher) {
        return ToolSearchToolCallAdvisor.builder()
          .toolSearcher(toolSearcher)
          .maxResults(5)
          .build();
    }

    @Bean
    ChatClient chatClient(ToolSearchToolCallAdvisor toolSearchToolCallAdvisor, OpenAiChatModel model) {
        return ChatClient.builder(model)
          .defaultTools(
            new FlightTools(),
            new RandomTools()
          )
          .defaultAdvisors(toolSearchToolCallAdvisor, new TokenCounterAdvisor())
          .build();
    }

    @Bean
    ChatClient chatClientWithoutToolsSearch(OpenAiChatModel model) {
        return ChatClient.builder(model)
          .defaultTools(
            new FlightTools(),
            new RandomTools()
          )
          .defaultAdvisors(new TokenCounterAdvisor())
          .build();
    }
}
