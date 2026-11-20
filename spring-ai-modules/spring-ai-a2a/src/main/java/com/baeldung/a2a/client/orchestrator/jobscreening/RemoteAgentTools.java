package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
class RemoteAgentTools {

    private final RemoteAgentClient remoteAgentClient;

    RemoteAgentTools(RemoteAgentClient remoteAgentClient) {
        this.remoteAgentClient = remoteAgentClient;
    }

    @Tool(
        name = "send-message-to-agent",
        description = "Sends a task to a remote agent and returns its response."
    )
    String sendMessageToAgent(
        @ToolParam(description = "Name of the remote agent") String agentName,
        @ToolParam(description = "The task to perform") String task
    ) throws ExecutionException, InterruptedException, TimeoutException {
        return remoteAgentClient.sendMessage(agentName, task);
    }
}