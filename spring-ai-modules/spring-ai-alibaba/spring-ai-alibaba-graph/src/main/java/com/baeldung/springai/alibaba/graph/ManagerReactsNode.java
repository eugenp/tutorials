package com.baeldung.springai.alibaba.graph;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import org.springframework.stereotype.Component;

@Component
class ManagerReactsNode implements NodeAction {

    private static final PromptTemplate PROMPT_TEMPLATE = new PromptTemplate("""
        You are a tired engineering manager listening to an employee explain themselves.

        Situation: {situation}
        Their excuse: {excuse}

        Reply in one sentence and rate how believable the excuse is, from 0.0 to 1.0.
        """);

    private final ChatClient chatClient;

    ManagerReactsNode(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Map<String, Object> apply(OverAllState state) {
        String situation = state.value("situation", String.class)
            .orElseThrow(IllegalStateException::new);
        String excuse = state.value("excuse", String.class)
            .orElseThrow(IllegalStateException::new);;

        ManagerReaction managerReaction = chatClient
            .prompt()
            .user(user -> user.text(PROMPT_TEMPLATE.getTemplate())
                .param("situation", situation)
                .param("excuse", excuse))
            .call()
            .entity(ManagerReaction.class);

        return Map.of(
            "managerReplies", managerReaction.reply(),
            "believability", managerReaction.believability()
        );
    }

    record ManagerReaction(
        String reply,
        Double believability
    ) {}
}