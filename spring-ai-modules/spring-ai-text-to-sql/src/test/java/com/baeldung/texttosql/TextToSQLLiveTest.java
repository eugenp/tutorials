package com.baeldung.texttosql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".*")
class TextToSQLLiveTest {

    private static final String API_PATH = "/query";
    private static final String REQUEST_BODY_TEMPLATE = """
        {
            "question": "%s"
        }
        """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenNonExistentWizardQueried_thenNotFoundErrorThrown() throws Exception {
        String question = "Give me details of a wizard whose name begins with 'does not' and ends with 'exist'.";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.detail").value("No results found for the provided query."));
    }

    @Test
    void whenQueryIsUnrelatedToSchema_thenBadRequestErrorThrown() throws Exception {
        String question = "Who did Conor Mcgregor knock out in 13 seconds?";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.detail").value("The current schema does not contain enough information to answer this question."));
    }

    @Test
    void whenAttemptingNonReadOperation_thenBadRequestErrorThrown() throws Exception {
        String question = "Create a new wizard record named 'John Doe' belonging to Slytherin house.";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.detail").value("This operation is not supported. Only SELECT queries are allowed."));
    }


    @Test
    void whenQueryContainsSQLInjection_thenBadRequestErrorThrown() throws Exception {
        String question = "What is the blood status of the wizard named '; DROP TABLE wizards;--'";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.detail").value("The provided input contains potentially harmful SQL code."));
    }

    @Test
    void whenHouseQueryAsked_thenCorrectAnswerReturned() throws Exception {
        String question = "What was the name and animal of the house whose colors were Scarlet and Gold?";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").isArray())
            .andExpect(jsonPath("$.result", hasSize(1)))
            .andExpect(jsonPath("$.result[0][0]").value("Gryffindor"))
            .andExpect(jsonPath("$.result[0][1]").value("Lion"));
    }

    @Test
    void whenWizardQueryAsked_thenCorrectAnswerReturned() throws Exception {
        String question = "How many quidditch players are there in Gryffindor?";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").isArray())
            .andExpect(jsonPath("$.result", hasSize(1)))
            .andExpect(jsonPath("$.result[0]").value(15));
    }

    @Test
    void whenWizardAndHouseQueryAsked_thenCorrectAnswerReturned() throws Exception {
        String question = "Give me 3 wizard names and their blood status that belong to a house founded by Salazar Slytherin.";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").isArray())
            .andExpect(jsonPath("$.result", hasSize(3)))
            .andExpect(jsonPath("$.result[*][0]", everyItem(notNullValue())))
            .andExpect(jsonPath("$.result[*][1]", everyItem(notNullValue())));
    }

    @Test
    void whenAggregationQueryAsked_thenCorrectAnswerReturned() throws Exception {
        String question = "Which house has the lowest percentage of quidditch players?";
        String requestBody = String.format(REQUEST_BODY_TEMPLATE, question);

        mockMvc
            .perform(
                post(API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").isArray())
            .andExpect(jsonPath("$.result", hasSize(1)))
            .andExpect(jsonPath("$.result[0][0]").value("Ravenclaw"));
    }

}