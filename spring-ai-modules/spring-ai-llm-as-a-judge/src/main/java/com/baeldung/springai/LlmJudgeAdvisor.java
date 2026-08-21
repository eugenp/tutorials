package com.baeldung.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.prompt.Prompt;

public class LlmJudgeAdvisor implements CallAdvisor {

    private static final String JUDGE_SYSTEM_PROMPT = """
            You are a strict quality evaluator for AI-generated answers.
            
            Given a user question and an AI-generated answer, rate the answer quality.
            
            Use this rubric:
            - 1.0: Complete, accurate, and clearly explained
            - 0.7: Mostly correct but missing details or clarity
            - 0.4: Partially correct or overly vague
            - 0.0: Incorrect, irrelevant, or harmful
            
            Respond ONLY with a valid JSON object. Do not add any explanation outside the JSON.
            Format: {"score": <0.0 to 1.0>, "feedback": "<one concise sentence>"}
            """;

    private final ChatClient judgeClient;
    private final double scoreThreshold;
    private final int maxRefinements;

    public LlmJudgeAdvisor(
            ChatClient judgeClient,
            double scoreThreshold,
            int maxRefinements
    ) {
        this.judgeClient = judgeClient;
        this.scoreThreshold = scoreThreshold;
        this.maxRefinements = maxRefinements;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        for (int attempt = 1; attempt <= maxRefinements + 1; attempt++) {
            ChatClientResponse response = chain.copy(this).nextCall(request);
            if(attempt > maxRefinements) {
                return response;
            };
            Verdict verdict = evaluate(request, response);
            if (verdict.score() >= scoreThreshold) {
                return response;
            }

            request = addFeedback(request, verdict.feedback());
        }
        return chain.copy(this).nextCall(request);
    }

    private Verdict evaluate(ChatClientRequest request, ChatClientResponse response) {
        String question = request.prompt().getUserMessage().getText();
        String answer = response.chatResponse().getResult().getOutput().getText();

        return judgeClient.prompt()
                .system(JUDGE_SYSTEM_PROMPT)
                .user("Question: " + question + "\n\nAnswer: " + answer)
                .call()
                .entity(Verdict.class);
    }

    private ChatClientRequest addFeedback(ChatClientRequest original, String feedback) {
        Prompt augmented = original.prompt()
                .augmentUserMessage(msg -> msg.mutate()
                        .text(msg.getText()
                                + "\n\nYour previous answer was insufficient. Feedback: " + feedback
                                + "\nPlease provide an improved answer.")
                        .build());
        return original.mutate().prompt(augmented).build();
    }

    @Override
    public String getName() {
        return "LlmJudgeAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}