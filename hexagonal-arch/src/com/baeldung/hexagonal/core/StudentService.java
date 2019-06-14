package com.baeldung.hexagonal.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.port.RepositoryPort;

@Service
public class StudentService {
    
    @Autowired
    private RepositoryPort repo;

    public void create(Integer id, String name) {
        repo.create(id, name);
    }
    
    public Map<String,Integer> getGrades(Integer id){
        return repo.getGrades(id);
    }
    
    public void setGrade(Integer id, String name, Integer grade) {
        repo.setGrade(id, name, grade);
    }
    
}
