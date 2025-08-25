package com.baeldung.agents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".*")
class RoutingWorkflowExecutorLiveTest {

    @Autowired
    private RoutingWorkflowExecutor routingExecutor;

    @Test
    void whenTechnicalQueryAsked_thenQuestionRoutedToTechnicalSupport() {
        String userInput = "My application keeps crashing when I try to upload files larger than 5MB. The error says 'java.lang.OutOfMemoryError: Java heap space'.";

        String response = routingExecutor.execute(userInput);
        
        assertThat(response)
            .isNotBlank()
            .containsAnyOf("memory", "heap", "JVM", "technical", "troubleshoot");
    }

    @Test
    void whenBillingSubscriptionQueryAsked_thenQuestionRoutedToBillingSupport() {
        String userInput = "I was charged twice for my subscription last month and need a refund";

        String response = routingExecutor.execute(userInput);
        
        assertThat(response)
            .isNotBlank()
            .containsAnyOf("refund", "billing", "charge", "payment", "apologize");
    }

    @Test
    void whenUnrelatedQueryAsked_thenNoRouteSelectedAndExceptionThrown() {
        String userInput = "Will Conor Mcgregor ever fight again?";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            routingExecutor.execute(userInput);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Could not resolve route for given user input.");
    }

    @TestConfiguration
    static class TestRouteHandlers {

        @Route(name = "Technical Support", description = "Technical support helping with troubleshooting, errors, and system issues")
        static class TechnicalSupportHandler implements RouteHandler {
            private final ChatClient chatClient;

            TechnicalSupportHandler(ChatClient.Builder chatClientBuilder) {
                this.chatClient = chatClientBuilder.build();
            }

            @Override
            public String handle(String input) {
                return chatClient
                    .prompt("""
                        You are a technical support specialist with expertise in software troubleshooting.
                        
                        User issue: %s
                        
                        Provide:
                        1. Likely cause of the problem
                        2. Step-by-step troubleshooting instructions
                        3. Potential solutions or workarounds
                        """.formatted(input))
                    .call()
                    .content();
            }
        }

        @Route(name = "Billing Support", description = "Billing support for inquiries related to payments, refunds, subscriptions, and invoices")
        static class BillingSupportHandler implements RouteHandler {
            private final ChatClient chatClient;

            BillingSupportHandler(ChatClient.Builder chatClientBuilder) {
                this.chatClient = chatClientBuilder.build();
            }

            @Override
            public String handle(String input) {
                return chatClient
                    .prompt("""
                        You are a professional billing specialist who handles payment and subscription issues.
                        
                        Customer inquiry: %s
                        
                        Address their concern by:
                        1. Acknowledging the billing issue
                        2. Explaining what might have happened
                        3. Providing clear resolution steps
                        """.formatted(input))
                    .call()
                    .content();
            }
        }

    }

}