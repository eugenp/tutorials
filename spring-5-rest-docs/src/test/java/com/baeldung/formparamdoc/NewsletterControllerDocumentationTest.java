package com.baeldung.formparamdoc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.FormParametersSnippet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = { NewsletterController.class},
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = { NewsletterController.class, SubscriptionService.class })
class NewsletterControllerDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())))
            .build();
    }

    @Test
    void whenFormRequestIsValid_thenDocumentFormParameters() throws Exception {
        mockMvc.perform(postSubscription())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andDo(document("newsletter-subscribe", docFormParams(), docResponseFields()));
    }

    private ResponseFieldsSnippet docResponseFields() {
        return responseFields(
            fieldWithPath("id").description("Generated subscription identifier"),
            fieldWithPath("email").description("Email copied from the request"),
            fieldWithPath("frequency").description("Frequency copied from the request"),
            fieldWithPath("topics[]").description("Topics copied from the request parameters"),
            fieldWithPath("marketingAccepted").description("Boolean flag copied from the request"));
    }

    private FormParametersSnippet docFormParams() {
        return formParameters(
            parameterWithName("email").description("The subscriber email address"),
            parameterWithName("name").description("The display name of the subscriber"),
            parameterWithName("frequency").description("Delivery frequency: weekly or monthly"),
            parameterWithName("topics").optional().description("One or more selected topic values"),
            parameterWithName("marketingAccepted").optional().description("Whether marketing messages are accepted"),
            parameterWithName("trackingId").ignored());
    }

    private MockHttpServletRequestBuilder postSubscription() {
        return post("/newsletter/subscriptions")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "api@baeldung.com")
                .param("name", "Baeldung API")
                .param("frequency", "weekly")
                .param("topics", "spring", "testing")
                .param("marketingAccepted", "true")
                .param("trackingId", "campaign-42");
    }

    @Test
    void whenRequiredFormParameterIsMissing_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/newsletter/subscriptions")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Baeldung API")
                .param("frequency", "weekly"))
            .andExpect(status().isBadRequest());
    }
}
