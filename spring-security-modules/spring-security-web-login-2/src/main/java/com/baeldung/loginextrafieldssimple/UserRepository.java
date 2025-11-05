package com.baeldung.loginextrafieldssimple;

public interface UserRepository {

    public User findUser(String username, String domain);
    
}
