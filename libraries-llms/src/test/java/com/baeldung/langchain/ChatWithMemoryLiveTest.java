package com.baeldung.langchain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;

import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatWithMemoryLiveTest {
    
    Logger logger = LoggerFactory.getLogger(ChatWithMemoryLiveTest.class);

    @Test
    public void givenMemory_whenPrompted_thenValidResponse() {

        ChatLanguageModel model = OpenAiChatModel.withApiKey(Constants.OPEN_AI_KEY);
        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(300, new OpenAiTokenizer(GPT_3_5_TURBO));

        chatMemory.add(userMessage("Hello, my name is Kumar"));
        AiMessage answer = model.generate(chatMemory.messages())
            .content();
        logger.info(answer.text());
        Assert.assertNotNull(answer.text());
        chatMemory.add(answer);

        chatMemory.add(userMessage("What is my name?"));
        AiMessage answerWithName = model.generate(chatMemory.messages())
            .content();
        logger.info(answerWithName.text());
        assertThat(answerWithName.text().contains("Kumar"));
        chatMemory.add(answerWithName);

    }

}
