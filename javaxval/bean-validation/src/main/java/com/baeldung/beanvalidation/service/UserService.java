package com.baeldung.beanvalidation.service;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.baeldung.beanvalidation.model.User;

public class UserService implements EntityService, Serializable {
    
    private static final long serialVersionUID = 1L;

    @Valid
    private User user;
    
    @NotNull(message = "FileName cannot be null")
    @Size(min = 5, max = 10, message = "FileName must be between 5 and 10 characters")
    private String fileName;
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public void processEntity() {
        // process the user here
    }

    @Override
    public String toString() {
        return "UserService [user=" + user + ", fileName=" + fileName + "]";
    }
}
