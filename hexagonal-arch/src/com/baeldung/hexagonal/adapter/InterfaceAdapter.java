package com.baeldung.hexagonal.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.StudentService;
import com.baeldung.hexagonal.port.InterfacePort;

@RestController
@RequestMapping("/")
public class InterfaceAdapter implements InterfacePort {

    @Autowired
    private StudentService ss;
    
    @Override
    public void create(Integer id, String name) {
        ss.create(id, name);
    }

    @Override
    public Map<String, Integer> getGrades(Integer id) {
        return ss.getGrades(id);
    }

    @Override
    public void setGrade(Integer id, String name, Integer grade) {
        ss.setGrade(id, name, grade);
    }

}
