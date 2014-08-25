package org.baeldung.persistence.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;
    @ValidUsername
    @NotNull
    @NotEmpty
    private String username;
   
    private Integer role; 
   
    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMatchingPassword() {
        return matchingPassword;
    }
    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [firstName=").append(firstName).append("]").
            append("[lastName=").append(lastName).append("]").append("[username").append(username).append("]").append("[password").append(password).append("]");
        return builder.toString();
    }
}
