package com.baeldung.sendgrid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Validated
@ConfigurationProperties(prefix = "com.baeldung.sendgrid")
public class SendGridConfigurationProperties {

    @NotBlank(message = "SendGrid API key must be configured")
    @Pattern(regexp = "^SG[0-9a-zA-Z._]{67}$", message = "Invalid SendGrid API key format")
    private String apiKey;

    @NotBlank(message = "SendGrid from email must be configured")
    @Email(message = "Invalid email format")
    private String fromEmail;

    @Nullable
    private String fromName;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

}