package com.baeldung.hexagonalarchitecture.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 15:57
 */
@Component
@ConfigurationProperties(prefix = "user-accounts")
@Validated
public class UserAccountConfigurableProperties {
    @NotBlank(message = "Email confirmation uri is required")
    private String emailConfirmationUri;

    public String getEmailConfirmationUri() {
        return emailConfirmationUri;
    }

    public void setEmailConfirmationUri(String emailConfirmationUri) {
        this.emailConfirmationUri = emailConfirmationUri;
    }
}
