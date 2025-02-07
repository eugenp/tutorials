package com.baeldung.springai.advisors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ai.autoconfigure.mistralai.MistralAiAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.*;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * To set the test environment:
 * populate OPENAI_API_KEY env. variable with active Open AI API key
 * */
@SpringBootTest(classes = {ChatModel.class, SimpleVectorStoreConfiguration.class})
@EnableAutoConfiguration(exclude = {RedisVectorStoreAutoConfiguration.class,
  MistralAiAutoConfiguration.class, MongoDBAtlasVectorStoreAutoConfiguration.class, MongoAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
public class SpringAILiveTest {

    @Autowired
    @Qualifier("openAiChatModel")
    ChatModel chatModel;
    @Autowired
    VectorStore vectorStore;
    ChatClient chatClient;

    @BeforeEach
    void setup() {
        chatClient = ChatClient.builder(chatModel).build();
    }

    @Test
    void givenMessageChatMemoryAdvisor_whenAskingChatToIncrementTheResponseWithNewName_thenNamesFromTheChatHistoryExistInResponse() {
        ChatMemory chatMemory = new InMemoryChatMemory();
        MessageChatMemoryAdvisor chatMemoryAdvisor = new MessageChatMemoryAdvisor(chatMemory);

        String responseContent = chatClient.prompt()
          .user("Add this name to a list and return all the values: Bob")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Bob");

        responseContent = chatClient.prompt()
          .user("Add this name to a list and return all the values: John")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Bob")
          .contains("John");

        responseContent = chatClient.prompt()
          .user("Add this name to a list and return all the values: Anna")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Bob")
          .contains("John")
          .contains("Anna");
    }

    @Test
    void givenPromptChatMemoryAdvisor_whenAskingChatToIncrementTheResponseWithNewName_thenNamesFromTheChatHistoryExistInResponse() {
        ChatMemory chatMemory = new InMemoryChatMemory();
        PromptChatMemoryAdvisor chatMemoryAdvisor = new PromptChatMemoryAdvisor(chatMemory);

        String responseContent = chatClient.prompt()
          .user("Add this name to a list and return all the values: Bob")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
                .contains("Bob");

        responseContent = chatClient.prompt()
          .user("Add this name to a list and return all the values: John")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Bob")
          .contains("John");

        responseContent = chatClient.prompt()
          .user("Add this name to a list and return all the values: Anna")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Bob")
          .contains("John")
          .contains("Anna");
    }

    @Test
    void givenVectorStoreChatMemoryAdvisor_whenAskingChatToIncrementTheResponseWithNewName_thenNamesFromTheChatHistoryExistInResponse() {
        VectorStoreChatMemoryAdvisor chatMemoryAdvisor = new VectorStoreChatMemoryAdvisor(vectorStore);

        String responseContent = chatClient.prompt()
          .user("Find cats from our chat history, add Lion there and return a list")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Lion");

        responseContent = chatClient.prompt()
          .user("Find cats from our chat history, add Puma there and return a list")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Lion")
          .contains("Puma");

        responseContent = chatClient.prompt()
          .user("Find cats from our chat history, add Leopard there and return a list")
          .advisors(chatMemoryAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("Lion")
          .contains("Puma")
          .contains("Leopard");
    }

    @Test
    void givenQuestionAnswerAdvisor_whenAskingQuestion_thenAnswerShouldBeProvidedBasedOnVectorStoreInformation() {

        Document document = new Document("The sky is green");
        List<Document> documents = new TokenTextSplitter().apply(List.of(document));
        vectorStore.add(documents);
        QuestionAnswerAdvisor questionAnswerAdvisor = new QuestionAnswerAdvisor(vectorStore);

        String responseContent = chatClient.prompt()
          .user("What is the sky color?")
          .advisors(questionAnswerAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .containsIgnoringCase("green");
    }

    @Test
    void givenSafeGuardAdvisor_whenSendPromptWithSensitiveWord_thenExpectedMessageShouldBeReturned() {

        List<String> forbiddenWords = List.of("Word2");
        SafeGuardAdvisor safeGuardAdvisor = new SafeGuardAdvisor(forbiddenWords);

        String responseContent = chatClient.prompt()
          .user("Please split the 'Word2' into characters")
          .advisors(safeGuardAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("I'm unable to respond to that due to sensitive content");
    }

    @Test
    void givenCustomLoggingAdvisor_whenSendPrompt_thenPromptTextAndResponseShouldBeLogged() {

        CustomLoggingAdvisor customLoggingAdvisor = new CustomLoggingAdvisor();

        String responseContent = chatClient.prompt()
          .user("Count from 1 to 10")
          .advisors(customLoggingAdvisor)
          .call()
          .content();

        assertThat(responseContent)
          .contains("1")
          .contains("10");
    }
}
