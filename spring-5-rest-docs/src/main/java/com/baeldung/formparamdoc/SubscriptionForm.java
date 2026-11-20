package com.baeldung.formparamdoc;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SubscriptionForm {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "weekly|monthly")
    private String frequency;

    private List<String> topics = new ArrayList<>();

    private boolean marketingAccepted;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics == null ? new ArrayList<>() : topics;
    }

    public boolean isMarketingAccepted() {
        return marketingAccepted;
    }

    public void setMarketingAccepted(boolean marketingAccepted) {
        this.marketingAccepted = marketingAccepted;
    }
}
