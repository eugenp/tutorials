package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Classroom;

import java.util.HashMap;
import java.util.Map;

public class PersistenceClassroomInMemoryAdapter implements PersistenceClassroomPort {
    Map<String, Classroom> classroomByName;

    PersistenceClassroomInMemoryAdapter() {
        this.classroomByName = new HashMap<>();
    }

    @Override
    public void save(Classroom classroom) {
        classroomByName.put(classroom.getName(), classroom);
    }

    @Override
    public Classroom find(String name) {
        return classroomByName.get(name);
    }
}
