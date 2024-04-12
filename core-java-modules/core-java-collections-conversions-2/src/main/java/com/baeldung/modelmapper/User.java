package com.baeldung.modelmapper;

/**
 * User model entity class
 *
 * @author Sasa Milenkovic
 */
public class User {

    private String userId;
    private String username;
    private String email;
    private String contactNumber;
    private String userType;

    // Standard constructors, getters and setters

    public User() {
    }

    public User(String userId, String username, String email, String contactNumber, String userType) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
