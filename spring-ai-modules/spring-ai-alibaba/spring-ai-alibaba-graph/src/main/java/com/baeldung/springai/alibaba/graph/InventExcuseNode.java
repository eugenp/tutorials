package com.baeldung.springai.alibaba.graph;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import org.springframework.stereotype.Component;

@Component
class InventExcuseNode implements NodeAction {

    private static final String PROMPT_TEMPLATE = """
        You're an employee explaining why you missed something at work.
        You're a bad liar, but a creative one.
        You never take accountability or admit to lying.

        Situation: {situation}
        The excuse you already gave: {previousExcuse}
        How your manager responded: {managerReplies}

        Give a new excuse in at most two sentences. If you already gave one, do not
        abandon it. Keep the original story and add a further complication.
        Respond with only the excuse.
        """;

    private final ChatClient chatClient;

    InventExcuseNode(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Map<String, Object> apply(OverAllState state) {
        String situation = state.value("situation", String.class)
            .orElseThrow(IllegalStateException::new);

        String previousExcuse = state.value("excuse", "none yet");
        List<String> managerReplies = state.value("managerReplies", List.of());
        Integer attempts = state.value("attempts", 0);

        String excuse = chatClient
            .prompt()
            .user(user -> user.text(PROMPT_TEMPLATE)
                .param("situation", situation)
                .param("previousExcuse", previousExcuse)
                .param("managerReplies", managerReplies.isEmpty()
                    ? "none yet"
                    : String.join("\n", managerReplies)))
            .call()
            .content();

        return Map.of(
            "excuse", excuse,
            "attempts", attempts + 1
        );
    }
}