package com.baeldung.repositoryvsdaopattern;

import java.util.List;

public interface UserRepository {
    
    User get(Long id);
    
    void add(User user);
    
    void update(User user);
    
    void remove(User user);
    
    User findByUserName(String userName);
    
    User findByEmail(String email);
    
    List<Tweet> fetchTweets(User user);
    
}
