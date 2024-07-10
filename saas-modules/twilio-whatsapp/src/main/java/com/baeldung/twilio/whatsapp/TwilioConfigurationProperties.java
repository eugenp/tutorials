package com.baeldung.twilio.whatsapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Getter
    @Setter
    public class NewArticleNotification {

        @NotBlank(message = "Content SID must be configured")
        @Pattern(regexp = "^HX[0-9a-fA-F]{32}$", message = "Invalid content SID format")
        private String contentSid;

    }

}