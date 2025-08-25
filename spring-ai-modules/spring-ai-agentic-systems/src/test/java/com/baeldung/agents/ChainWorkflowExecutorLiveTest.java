package com.baeldung.agents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".*")
class ChainWorkflowExecutorLiveTest {

    @Autowired
    private ChainWorkflowExecutor chainWorkflowExecutor;

    private static final String USER_INPUT = """
        Endpoint: POST /api/v1/products/{productId}/reviews
        Purpose: Submit a new review for a specific product
        Input: JSON with rating (integer 1-5 required), title (string max 100 chars),
               comment (string max 500 chars optional), verified_purchase (boolean),
               images (array of URLs, max 5 images)
        Auth: Bearer token required, user must have purchased the product
        Response: 201 with review object including id and timestamp,
                  400 for invalid rating or text too long,
                  401 if not authenticated,
                  403 if user hasn't purchased product,
                  404 if product doesn't exist,
                  409 if user already reviewed this product
        Business Rules: One review per user per product, can edit within 30 days,
                        verified_purchase flag auto-set based on order history
        Rate Limit: 10 reviews per user per day      
        """;

    @Test
    void whenRawApiEndpointInfoProvided_thenComprehensiveDocumentationGenerated() {
        String documentation = chainWorkflowExecutor.execute(USER_INPUT);

        assertThat(documentation)
            .isNotBlank()
            .contains("POST", "/api/v1/products/{productId}/reviews");
    }

}