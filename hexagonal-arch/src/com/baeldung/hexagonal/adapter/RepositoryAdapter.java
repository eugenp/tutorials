package com.baeldung.hexagonal.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.core.Student;
import com.baeldung.hexagonal.port.RepositoryPort;

@Component
public class RepositoryAdapter implements RepositoryPort {
    
    private List<Student> students = new ArrayList<Student>();

    @Override
    public void create(Integer id, String name) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        students.add(student);
    }

    @Override
    public Map<String, Integer> getGrades(Integer id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student.getGrades();
            }
        }
        return null;
    }

    @Override
    public void setGrade(Integer id, String name, Integer grade) {
        for (Student student : students) {
            if (student.getId() == id) {
                Map<String, Integer> grades = student.getGrades();
                if (grades == null) {
                    grades = new HashMap<String, Integer>();
                }
                grades.put(name, grade);
                student.setGrades(grades);
            }
        }
    }
    
    

}
