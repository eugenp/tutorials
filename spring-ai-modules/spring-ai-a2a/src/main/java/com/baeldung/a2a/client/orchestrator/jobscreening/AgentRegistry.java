package com.baeldung.a2a.client.orchestrator.jobscreening;

import io.a2a.A2A;
import io.a2a.spec.AgentCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class AgentRegistry {

    private final Map<String, AgentCard> agentCards = new HashMap<>();

    AgentRegistry(@Value("${remote.agents.urls}") List<String> agentUrls) throws URISyntaxException {
        for (String url : agentUrls) {
            String path = new URI(url).getPath();
            AgentCard card = A2A.getAgentCard(url, path + ".well-known/agent-card.json", null);
            agentCards.put(card.name(), card);
        }
    }

    AgentCard get(String agentName) {
        return agentCards.get(agentName);
    }

    String describeAgents() {
        return agentCards
            .values()
            .stream()
            .map(card -> "- " + card.name() + ": " + card.description())
            .collect(Collectors.joining("\n"));
    }
}