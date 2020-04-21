package com.baeldung.modelmapper;

/**
 * UserDTO model class
 *
 * @author Sasa Milenkovic
 */
public class UserDTO {

    private String userId;
    private String userName;
    private String email;

    // getters and setters

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


}
