package com.baeldung.agents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class RoutingWorkflowExecutor {

    private static final Logger log = LoggerFactory.getLogger(RoutingWorkflowExecutor.class);
    
    private final ChatClient chatClient;
    private final PromptTemplate systemPrompt;
    private final Map<String, RouteHandler> routes;
    
    RoutingWorkflowExecutor(
        ChatClient.Builder chatClientBuilder,
        @Value("classpath:routing-workflow/system-prompt.st") Resource systemPrompt,
        List<RouteHandler> handlers
    ) throws IOException {
        this.chatClient = chatClientBuilder.build();
        this.systemPrompt = new PromptTemplate(systemPrompt);
        this.routes = handlers.stream()
            .collect(Collectors.toMap(
                handler -> handler.getClass().getAnnotation(Route.class).name(),
                Function.identity()
            ));
    }
    
    String execute(String userInput) {
        String routesDescription = routes
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + ": " + 
                         entry.getValue().getClass().getAnnotation(Route.class).description())
            .collect(Collectors.joining("\n"));

        RouteDecision decision = chatClient
            .prompt(systemPrompt.render(Map.of(
                "input", userInput,
                "routes", routesDescription
            )))
            .call()
            .entity(RouteDecision.class);
        log.info("Routing decision made: {}", decision);

        RouteHandler handler = Optional
            .ofNullable(routes.get(decision.category()))
            .orElseThrow(() -> new IllegalStateException("Could not resolve route for given user input."));
        return handler.handle(userInput);
    }

}