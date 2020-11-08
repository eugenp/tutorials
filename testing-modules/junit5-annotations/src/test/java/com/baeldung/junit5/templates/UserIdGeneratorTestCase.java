package com.baeldung.junit5.templates;

public class UserIdGeneratorTestCase {
    private String displayName;
    private boolean isFeatureEnabled;
    private String firstName;
    private String lastName;
    private String expectedUserId;

    public UserIdGeneratorTestCase(String displayName, boolean isFeatureEnabled, String firstName, String lastName, String expectedUserId) {
        this.displayName = displayName;
        this.isFeatureEnabled = isFeatureEnabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expectedUserId = expectedUserId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isFeatureEnabled() {
        return isFeatureEnabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getExpectedUserId() {
        return expectedUserId;
    }
}
