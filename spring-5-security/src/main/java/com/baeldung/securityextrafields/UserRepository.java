package com.baeldung.securityextrafields;

public interface UserRepository {

    public User findUser(String username, String domain);
    
}
