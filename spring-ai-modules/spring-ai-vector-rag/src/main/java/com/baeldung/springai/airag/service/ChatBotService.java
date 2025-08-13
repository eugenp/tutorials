package com.baeldung.springai.airag.service;

import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {
    @Autowired
    @Qualifier("openAiChatModel")
    private ChatModel chatClient;
    @Autowired
    private DataRetrievalService dataRetrievalService;

    private final String PROMPT_BLUEPRINT = """
        Answer the query strictly referring the provided context:
        {context}
        Query:
        {query}
        In case you don't have any answer from the context provided, just say:
        I'm sorry I don't have the information you are looking for.
    """;

    public String chat(String query) {
        return chatClient.call(createPrompt(query, dataRetrievalService.searchData(query)));
    }

    private String createPrompt(String query, List<Document> context) {
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_BLUEPRINT);
        promptTemplate.add("query", query);
        promptTemplate.add("context", context);
        return promptTemplate.render();
    }
}
