package com.baeldung.sendgrid;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.MatchType;
import org.mockserver.model.JsonBody;
import org.mockserver.springtest.MockServerTest;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@ActiveProfiles("integration-test")
@MockServerTest("server.url=http://localhost:${mockServerPort}")
@ContextConfiguration(classes = TestSendGridConfiguration.class)
@EnableConfigurationProperties(SendGridConfigurationProperties.class)
class EmailDispatcherIntegrationTest {

    private MockServerClient mockServerClient;

    @Autowired
    private EmailDispatcher emailDispatcher;
    
    @Autowired
    private SendGridConfigurationProperties sendGridConfigurationProperties;
    
    private static final String SENDGRID_EMAIL_API_PATH = "/v3/mail/send";

    @Test
    void whenDispatchEmailCalled_thenSendGridApiCalledWithCorrectJsonBody() throws IOException {
        // Set up test data
        String toEmail = RandomString.make() + "@baeldung.it";
        String emailSubject = RandomString.make();
        String emailBody = RandomString.make();
        String fromName = sendGridConfigurationProperties.getFromName();
        String fromEmail = sendGridConfigurationProperties.getFromEmail();
        String apiKey = sendGridConfigurationProperties.getApiKey();
        
        // Create JSON body
        String jsonBody = String.format("""
            {
                "from": {
                    "name": "%s",
                    "email": "%s"
                },
                "subject": "%s",
                "personalizations": [{
                    "to": [{
                        "email": "%s"
                    }]
                }],
                "content": [{
                    "value": "%s"
                }]
            }
            """, fromName, fromEmail, emailSubject, toEmail, emailBody);
        
        // Configure mock server expectations
        mockServerClient
            .when(request()
                .withMethod("POST")
                .withPath(SENDGRID_EMAIL_API_PATH)
                .withHeader("Authorization", "Bearer " + apiKey)
                .withBody(new JsonBody(jsonBody, MatchType.ONLY_MATCHING_FIELDS)
            ))
            .respond(response().withStatusCode(202));
        
        // Invoke method under test
        emailDispatcher.dispatchEmail(toEmail, emailSubject, emailBody);
    
        // Verify the expected request was made
        mockServerClient
            .verify(request()
                .withMethod("POST")
                .withPath(SENDGRID_EMAIL_API_PATH)
                .withHeader("Authorization", "Bearer " + apiKey)
                .withBody(new JsonBody(jsonBody, MatchType.ONLY_MATCHING_FIELDS)
            ), VerificationTimes.once());
    }
    
    @Test
    void whenDispatchEmailCalledWithAttachment_thenSendGridApiCalledWithCorrectJsonBody() throws IOException {
        // Set up test data
        String toEmail = RandomString.make() + "@baeldung.it";
        String emailSubject = RandomString.make();
        String emailBody = RandomString.make();
        String fromName = sendGridConfigurationProperties.getFromName();
        String fromEmail = sendGridConfigurationProperties.getFromEmail();
        String apiKey = sendGridConfigurationProperties.getApiKey();
        String fileName = RandomString.make() + ".txt";
        MultipartFile attachment = createTextFile(fileName);
        
        // Create JSON body
        String jsonBody = String.format("""
            {
                "from": {
                    "name": "%s",
                    "email": "%s"
                },
                "subject": "%s",
                "personalizations": [{
                    "to": [{
                        "email": "%s"
                    }]
                }],
                "content": [{
                    "value": "%s"
                }],
                "attachments": [{
                    "filename": "%s"
                }]
            }
            """, fromName, fromEmail, emailSubject, toEmail, emailBody, fileName);
        
        // Configure mock server expectations
        mockServerClient
            .when(request()
                .withMethod("POST")
                .withPath(SENDGRID_EMAIL_API_PATH)
                .withHeader("Authorization", "Bearer " + apiKey)
                .withBody(new JsonBody(jsonBody, MatchType.ONLY_MATCHING_FIELDS)
            ))
            .respond(response().withStatusCode(202));
        
        // Invoke method under test
        emailDispatcher.dispatchEmail(toEmail, emailSubject, emailBody, List.of(attachment));
    
        // Verify the expected request was made
        mockServerClient
            .verify(request()
                .withMethod("POST")
                .withPath(SENDGRID_EMAIL_API_PATH)
                .withHeader("Authorization", "Bearer " + apiKey)
                .withBody(new JsonBody(jsonBody, MatchType.ONLY_MATCHING_FIELDS)
            ), VerificationTimes.once());
    }
    
    @Test
    void whenDispatchHydrationAlertCalled_thenSendGridApiCalledWithCorrectJsonBody() throws IOException {
        // Set up test data
        String username = RandomString.make();
        String toEmail = username + "@baeldung.it";
        String templateId = sendGridConfigurationProperties.getHydrationAlertNotification().getTemplateId();
        String fromName = sendGridConfigurationProperties.getFromName();
        String fromEmail = sendGridConfigurationProperties.getFromEmail();
        String apiKey = sendGridConfigurationProperties.getApiKey();
        
        // Create JSON body
        String jsonBody = String.format("""
            {
                "from": {
                    "name": "%s",
                    "email": "%s"
                },
                "personalizations": [{
                    "to": [{
                        "email": "%s"
                    }],
                    "dynamic_template_data": {
                        "name": "%s"
                    }
                }],
                "template_id": "%s"
            }
            """, fromName, fromEmail, toEmail, username, templateId);
        
        // Configure mock server expectations
        mockServerClient
            .when(request()
                .withMethod("POST")
                .withPath(SENDGRID_EMAIL_API_PATH)
                .withHeader("Authorization", "Bearer " + apiKey)
                .withBody(new JsonBody(jsonBody, MatchType.ONLY_MATCHING_FIELDS)
            ))
            .respond(response().withStatusCode(202));
        
        // Invoke method under test
        emailDispatcher.dispatchHydrationAlert(toEmail, username);
    
        // Verify the expected request was made
        mockServerClient
            .verify(request()
                .withMethod("POST")
                .withPath(SENDGRID_EMAIL_API_PATH)
                .withHeader("Authorization", "Bearer " + apiKey)
                .withBody(new JsonBody(jsonBody, MatchType.ONLY_MATCHING_FIELDS)
            ), VerificationTimes.once());
    }
    
    private MultipartFile createTextFile(String fileName) throws IOException {
        byte[] fileContentBytes = RandomString.make().getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContentBytes);
        return new MockMultipartFile(fileName, fileName, "text/plain", inputStream);
    }

}