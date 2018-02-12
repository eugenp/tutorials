package com.baeldung.demo.profile;

public class ClientProfile {
    private String userId;
    private String firstName;
    private String lastName;
    
    public ClientProfile(String userId, String firstName, String lastName) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getUserId() {
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

}
