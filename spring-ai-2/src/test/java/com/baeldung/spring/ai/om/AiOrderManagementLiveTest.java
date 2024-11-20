package com.baeldung.spring.ai.om;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "classpath:/order_mgmt.sql", executionPhase = BEFORE_TEST_CLASS)
public class AiOrderManagementLiveTest {

    Logger logger = LoggerFactory.getLogger(AiOrderManagementLiveTest.class);
    @Autowired
    private Function<CreateOrderRequest, Long> createOrderFn;

    @Autowired
    private ChatModel chatClient;

    @ParameterizedTest
    @ValueSource(strings = {"Create an order with quantity 20 for user id Jenny, "
        + "and randomly generate a positive whole number for the order ID",
          "Create two orders. The first order is for user id Sophia with quantity 30."
        + " The second order is for user id Mary with quantity 40."
        + " Randomly generate positive whole numbers for the order IDs."})
    void whenOrderInfoProvided_thenSaveInDB(String promptString) {
        Prompt prompt  = new Prompt(promptString, OpenAiChatOptions
            .builder()
            .withFunction("createOrderFn")
            .build()
        );

        ChatResponse response = this.chatClient.call(prompt);
        logger.info("The response from the LLM service: {}", response.getResult().getOutput().getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"get all the orders of user id Alex",
    "get all the order of user ids Alex and John"})
    void whenUserIDProvided_thenFetchAllOrdersOfTheUser(String promptString) {
        Prompt prompt  = new Prompt(promptString, OpenAiChatOptions
            .builder()
            .withFunction("getUserOrders")
            .build()
        );

        ChatResponse response = this.chatClient.call(prompt);
        logger.info("The response from the LLM service: {}", response.getResult().getOutput().getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Create an order for user id Alex with quantity 25. "
            + "Don't create any order if the user has more than 2 orders. "
            + "While creating the order randomly generate positive whole numbers for the order ID",
        "Create an order for user id David with quantity 25. "
            + "Don't create any order if the user has more than 2 orders. "
            + "While creating the order randomly generate positive whole numbers for the order ID"
    })
    void whenUserIDProvided_thenCreateOrderOnlyIfTheUserHasNotMoreThanTwoOrders(String promptString) {
        Prompt prompt  = new Prompt(promptString, OpenAiChatOptions
            .builder()
            .withFunctions(Set.of("getUserOrders", "createOrderFn"))
            .build()
        );
        ChatResponse response = this.chatClient.call(prompt);
        logger.info("The response from the LLM service: {}", response.getResult().getOutput().getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Create an order with quantity 20"})
    void whenIncompleteOrderInfoProvided_thenAskUserForMoreInfo(String promptString) {

        Prompt prompt  = new Prompt(promptString, OpenAiChatOptions
            .builder()
            .withFunction("createOrderFn")
            .build()
        );

        ChatResponse response = this.chatClient.call(prompt);
        logger.info("The response from the LLM service: {}", response.getResult().getOutput().getContent());
    }
}
