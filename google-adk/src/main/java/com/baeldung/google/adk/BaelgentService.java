package com.baeldung.google.adk;

import com.google.adk.agents.BaseAgent;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
class BaelgentService {

    private final InMemoryRunner runner;
    private final ConcurrentMap<String, Session> inMemorySessionCache = new ConcurrentHashMap<>();

    BaelgentService(BaseAgent baseAgent) {
        this.runner = new InMemoryRunner(baseAgent);
    }

    BaelgentResponse interact(BaelgentRequest request) {
        UUID userId = request.userId() != null ? request.userId() : UUID.randomUUID();
        UUID sessionId = request.sessionId() != null ? request.sessionId() : UUID.randomUUID();

        String cacheKey = userId + ":" + sessionId;
        Session session = inMemorySessionCache.computeIfAbsent(cacheKey, key ->
            runner.sessionService()
                .createSession(runner.appName(), userId.toString(), null, sessionId.toString())
                .blockingGet()
        );

        Content userMessage = Content.fromParts(Part.fromText(request.question()));
        StringBuilder answerBuilder = new StringBuilder();
        runner.runAsync(userId.toString(), session.id(), userMessage)
            .blockingForEach(event -> {
                String content = event.stringifyContent();
                if (content != null && !content.isBlank()) {
                    answerBuilder.append(content);
                }
            });

        return new BaelgentResponse(userId, sessionId, answerBuilder.toString());
    }

}