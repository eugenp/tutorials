package com.baeldung.model;

import java.util.List;

/**
 * @author sasam0320
 * @date 4/18/2020
 */
public class User {

    private String userId;
    private String userName;
    private String email;
    private String contactNumber;
    private String userType;

    // Standard constructors, getters and setters

    public User(){}

    public User(String userId, String userName, String email, String contactNumber, String userType) {
        this.userId = userId;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
