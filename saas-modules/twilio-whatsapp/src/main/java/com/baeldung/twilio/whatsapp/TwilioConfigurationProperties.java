package com.baeldung.twilio.whatsapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Validated
@ConfigurationProperties(prefix = "com.baeldung.twilio")
public class TwilioConfigurationProperties {

    @NotBlank(message = "Twilio account SID must be configured")
    @Pattern(regexp = "^AC[0-9a-fA-F]{32}$", message = "Invalid Twilio account SID format")
    private String accountSid;

    @NotBlank(message = "Twilio auth token must be configured")
    private String authToken;

    @NotBlank(message = "Twilio messaging SID must be configured")
    @Pattern(regexp = "^MG[0-9a-fA-F]{32}$", message = "Invalid Twilio messaging SID format")
    private String messagingSid;

    @Valid
    private NewArticleNotification newArticleNotification = new NewArticleNotification();

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getMessagingSid() {
        return messagingSid;
    }

    public void setMessagingSid(String messagingSid) {
        this.messagingSid = messagingSid;
    }

    public NewArticleNotification getNewArticleNotification() {
        return newArticleNotification;
    }

    public void setNewArticleNotification(NewArticleNotification newArticleNotification) {
        this.newArticleNotification = newArticleNotification;
    }

    public class NewArticleNotification {

        @NotBlank(message = "Content SID must be configured")
        @Pattern(regexp = "^HX[0-9a-fA-F]{32}$", message = "Invalid content SID format")
        private String contentSid;

        public String getContentSid() {
            return contentSid;
        }

        public void setContentSid(String contentSid) {
            this.contentSid = contentSid;
        }

    }

}