package com.baeldung.twilio.whatsapp;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.ParameterBody;
import org.mockserver.springtest.MockServerTest;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.twilio.Twilio;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@MockServerTest
@ActiveProfiles("integration-test")
@EnableConfigurationProperties(TwilioConfigurationProperties.class)
class WhatsAppMessageDispatcherIntegrationTest {

    private MockServerClient mockServerClient;

    @Autowired
    private WhatsAppMessageDispatcher whatsAppMessageDispatcher;

    @Autowired
    private TwilioConfigurationProperties twilioConfigurationProperties;
    
    private String twilioApiPath;

    private static final String EMPTY_JSON = "{}";

    @BeforeEach
    void setUp() throws Exception {
        String accountSid = twilioConfigurationProperties.getAccountSid();
        String authToken = twilioConfigurationProperties.getAuthToken();

        InetSocketAddress remoteAddress = mockServerClient.remoteAddress();
        String host = remoteAddress.getHostName();
        int port = remoteAddress.getPort();

        TwilioProxyClient twilioProxyClient = new TwilioProxyClient(accountSid, authToken, host, port);
        Twilio.setRestClient(twilioProxyClient.createHttpClient());
        
        twilioApiPath = String.format("/2010-04-01/Accounts/%s/Messages.json", accountSid);
    }

    @Test
    void whenNewArticleNotificationDispatched_thenTwilioApiCalledWithCorrectParameters() {
        // Set up test data
        String contentSid = twilioConfigurationProperties.getNewArticleNotification().getContentSid();
        String messagingSid = twilioConfigurationProperties.getMessagingSid();
        String contactNumber = "+911001001000";
        String articleUrl = RandomString.make();

        // Configure mock server expectations
        mockServerClient
          .when(request()
            .withMethod("POST")
            .withPath(twilioApiPath)
            .withBody(new ParameterBody(
                param("To", String.format("whatsapp:%s", contactNumber)),
                param("ContentSid", contentSid),
                param("ContentVariables", String.format("{\"ArticleURL\":\"%s\"}", articleUrl)),
                param("MessagingServiceSid", messagingSid)
            ))
          )
          .respond(response()
            .withStatusCode(200)
            .withBody(EMPTY_JSON));

        // Invoke method under test
        whatsAppMessageDispatcher.dispatchNewArticleNotification(contactNumber, articleUrl);
        
        // Verify the expected request was made
        mockServerClient.verify(request()
          .withMethod("POST")
          .withPath(twilioApiPath)
          .withBody(new ParameterBody(
              param("To", String.format("whatsapp:%s", contactNumber)),
              param("ContentSid", contentSid),
              param("ContentVariables", String.format("{\"ArticleURL\":\"%s\"}", articleUrl)),
              param("MessagingServiceSid", messagingSid)
          )),
          VerificationTimes.once()
        );
    }

    @Test
    void whenReplyMessageDispatched_thenTwilioApiCalledWithCorrectParameters() {
        // Set up test data
        String username = RandomString.make();
        String messageBody = String.format("Hey %s, our team will get back to you shortly.", username);
        String messagingSid = twilioConfigurationProperties.getMessagingSid();
        String contactNumber = "+911001001000";

        // Configure mock server expectations
        mockServerClient
          .when(request()
            .withMethod("POST")
            .withPath(twilioApiPath)
            .withBody(new ParameterBody(
                param("To", String.format("whatsapp:%s", contactNumber)),
                param("MessagingServiceSid", messagingSid),
                param("Body", messageBody)
            ))
          )
          .respond(response()
            .withStatusCode(200)
            .withBody(EMPTY_JSON));

        // Invoke method under test
        whatsAppMessageDispatcher.dispatchReplyMessage(contactNumber, username);
        
        // Verify the expected request was made 
        mockServerClient.verify(request()
          .withMethod("POST")
          .withPath(twilioApiPath)
          .withBody(new ParameterBody(
              param("To", String.format("whatsapp:%s", contactNumber)),
              param("MessagingServiceSid", messagingSid),
              param("Body", messageBody)
          )),
          VerificationTimes.once()
        );
    }

}
