package com.baeldung.model;

import com.baeldung.customvalidator.FieldsValueMatch;

@FieldsValueMatch.List({ @FieldsValueMatch(field = "password", fieldMatch = "verifyPassword", message = "Passwords do not match!"), @FieldsValueMatch(field = "email", fieldMatch = "verifyEmail", message = "Email addresses do not match!") })
public class NewUserForm {
    private String email;
    private String verifyEmail;
    private String password;
    private String verifyPassword;

    public NewUserForm() {
    }

    public NewUserForm(String email, String verifyEmail, String password, String verifyPassword) {
        super();
        this.email = email;
        this.verifyEmail = verifyEmail;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(String verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

}
