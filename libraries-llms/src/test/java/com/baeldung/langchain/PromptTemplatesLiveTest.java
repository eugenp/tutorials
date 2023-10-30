package com.baeldung.langchain;

import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class PromptTemplatesLiveTest {
    
    private static final Logger logger = LoggerFactory.getLogger(PromptTemplatesLiveTest.class);

    @Test
    public void givenPromptTemplate_whenSuppliedInput_thenValidResponse() {
        PromptTemplate promptTemplate = PromptTemplate.from("Tell me a {{adjective}} joke about {{content}}..");
        Map<String, Object> variables = new HashMap<>();
        variables.put("adjective", "funny");
        variables.put("content", "humans");
        Prompt prompt = promptTemplate.apply(variables);

        ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(Constants.OPENAI_API_KEY)
            .modelName(GPT_3_5_TURBO)
            .temperature(0.3)
            .build();

        String response = model.generate(prompt.text());
        logger.info(response);
        assertNotNull(response);
    }

}
