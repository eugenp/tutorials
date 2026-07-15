package com.baeldung.a2a.client.orchestrator.jobscreening;

import io.a2a.A2A;
import io.a2a.client.Client;
import io.a2a.client.ClientEvent;
import io.a2a.client.MessageEvent;
import io.a2a.client.TaskEvent;
import io.a2a.client.config.ClientConfig;
import io.a2a.client.transport.jsonrpc.JSONRPCTransport;
import io.a2a.client.transport.jsonrpc.JSONRPCTransportConfig;
import io.a2a.spec.AgentCard;
import io.a2a.spec.Artifact;
import io.a2a.spec.Message;
import io.a2a.spec.Part;
import io.a2a.spec.TextPart;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
class RemoteAgentTools {

    private final AgentRegistry agentRegistry;

    RemoteAgentTools(AgentRegistry agentRegistry) {
        this.agentRegistry = agentRegistry;
    }

    @Tool(
        name = "send-message-to-agent",
        description = "Sends a task to a remote agent and returns its response."
    )
    String sendMessageToAgent(
        @ToolParam(description = "Name of the remote agent") String agentName,
        @ToolParam(description = "The task to perform") String task
    ) throws ExecutionException, InterruptedException, TimeoutException {
        AgentCard agentCard = agentRegistry.get(agentName);

        CompletableFuture<String> response = new CompletableFuture<>();
        BiConsumer<ClientEvent, AgentCard> responseConsumer = (event, card) -> {
            TaskEvent taskEvent = (TaskEvent) event;
            response.complete(taskEvent.getTask()
                .getArtifacts()
                .stream()
                .map(Artifact::parts)
                .map(this::extractText)
                .collect(Collectors.joining("\n")));
        };

        Client client = Client.builder(agentCard)
            .clientConfig(new ClientConfig.Builder()
                .setAcceptedOutputModes(List.of("text"))
                .build())
            .withTransport(JSONRPCTransport.class, new JSONRPCTransportConfig())
            .addConsumers(List.of(responseConsumer))
            .streamingErrorHandler(response::completeExceptionally)
            .build();

        Message message = A2A.toUserMessage(task);
        client.sendMessage(message);
        return response.get(60, TimeUnit.SECONDS);
    }

    private String extractText(List<Part<?>> parts) {
        return parts
            .stream()
            .filter(TextPart.class::isInstance)
            .map(TextPart.class::cast)
            .map(TextPart::getText)
            .collect(Collectors.joining("\n"));
    }
}