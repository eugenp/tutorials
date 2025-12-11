package com.baeldung.simpleopenai;

import java.lang.System.Logger;

import io.github.sashirestela.openai.SimpleOpenAIGeminiGoogle;

public final class Client {

    public static final Logger LOGGER = System.getLogger("simpleopenai");
    public static final String CHAT_MODEL = "gemini-2.5-flash";
    public static final String EMBEDDING_MODEL = "text-embedding-004";

    private Client() {
    }

    public static SimpleOpenAIGeminiGoogle getClient() {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY is not set");
        }
        return SimpleOpenAIGeminiGoogle.builder()
            .apiKey(apiKey)
            .build();
    }
}
