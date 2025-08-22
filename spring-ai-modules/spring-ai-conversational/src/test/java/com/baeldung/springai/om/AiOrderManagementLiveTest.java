package com.baeldung.springai.om;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import java.util.Set;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("aiassistant")
@Sql(scripts = "classpath:/order_mgmt.sql", executionPhase = BEFORE_TEST_CLASS)
public class AiOrderManagementLiveTest {

    Logger logger = LoggerFactory.getLogger(AiOrderManagementLiveTest.class);

    @Autowired
    private OrderManagementAIAssistant orderManagementAIAssistant;

    @ParameterizedTest
    @ValueSource(strings = { "Create an order with quantity 20 for user id Jenny, " +
        "and randomly generate a positive whole number for the order ID",
        "Create two orders. The first order is for user id Sophia with quantity 30."
            + " The second order is for user id Mary with quantity 40."
            + " Randomly generate positive whole numbers for the order IDs." })
    void whenOrderInfoProvided_thenSaveInDB(String promptString) {
       ChatResponse response = this.orderManagementAIAssistant
           .callChatClient(Set.of("createOrderFn"), promptString);
        String resultContent = response.getResult().getOutput().getText();
        logger.info("The response from the LLM service: {}", resultContent);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Get all the orders of user id Alex",
    "Get all the order of user ids Alex and John"})
    void whenUserIDProvided_thenFetchUserOrders(String promptString) {
        ChatResponse response = this.orderManagementAIAssistant
            .callChatClient(Set.of("getUserOrdersFn"), promptString);
        String resultContent = response.getResult()
            .getOutput()
            .getText();
        logger.info("The response from the LLM service: {}", resultContent);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Create an order for user id Alex with quantity 25. "
            + "Don't create any order if the user has more than 2 orders. "
            + "While creating the order, randomly generate positive whole numbers for the order ID",
        "Create an order for user id David with quantity 25. "
            + "Don't create any order if the user has more than 2 orders. "
            + "While creating the order randomly generate positive whole numbers for the order ID"
    })
    void whenUserIDProvided_thenCreateOrderIfUserHasLessThanTwoOrders(String promptString) {
        ChatResponse response = this.orderManagementAIAssistant
            .callChatClient(Set.of("getUserOrdersFn", "createOrderFn"), promptString);
        String resultContent = response.getResult()
            .getOutput()
            .getText();
        logger.info("The response from the LLM service: {}", resultContent);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Create an order with a quantity of 20"})
    void whenIncompleteOrderInfoProvided_thenAskUserForMoreInfo(String promptString) {
        ChatResponse response = this.orderManagementAIAssistant
            .callChatClient(Set.of("createOrderFn"), promptString);
        String resultContent = response.getResult()
            .getOutput()
            .getText();
        logger.info("The response from the LLM service: {}", resultContent);
    }
}
