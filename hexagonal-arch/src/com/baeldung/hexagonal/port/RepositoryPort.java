package com.baeldung.hexagonal.port;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPort {
    
    void create(Integer id, String name);
    
    Map<String,Integer> getGrades(Integer id);
    
    void setGrade(Integer id, String name, Integer grade);
    
}
