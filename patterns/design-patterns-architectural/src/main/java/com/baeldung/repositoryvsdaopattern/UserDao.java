package com.baeldung.repositoryvsdaopattern;

public interface UserDao {
    
    void create(User user);
    
    User read(Long id);
    
    void update(User user);
    
    void delete(String userName);

}
