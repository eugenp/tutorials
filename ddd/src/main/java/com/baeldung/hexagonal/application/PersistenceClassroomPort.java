package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Classroom;

public interface PersistenceClassroomPort {
    void save(Classroom classroom);

    Classroom find(String name);
}
