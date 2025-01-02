package com.baeldung.loginextrafieldscustom;

public interface UserRepository {

    public User findUser(String username, String domain);
    
}
