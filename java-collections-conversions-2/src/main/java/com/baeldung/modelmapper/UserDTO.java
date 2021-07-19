package com.baeldung.modelmapper;

/**
 * UserDTO model class
 *
 * @author Sasa Milenkovic
 */
public class UserDTO {

    private String userId;
    private String username;
    private String email;

    // getters and setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
