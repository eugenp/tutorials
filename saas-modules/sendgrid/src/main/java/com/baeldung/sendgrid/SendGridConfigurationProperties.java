package com.baeldung.sendgrid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
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

    @NotBlank
    private String fromName;

    @Valid
    private HydrationAlertNotification hydrationAlertNotification = new HydrationAlertNotification();

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

    public HydrationAlertNotification getHydrationAlertNotification() {
        return hydrationAlertNotification;
    }

    public void setHydrationAlertNotification(HydrationAlertNotification hydrationAlertNotification) {
        this.hydrationAlertNotification = hydrationAlertNotification;
    }

    class HydrationAlertNotification {

        @NotBlank(message = "Template-id must be configured")
        @Pattern(regexp = "^d-[a-f0-9]{32}$", message = "Invalid template ID format")
        private String templateId;

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

    }

}