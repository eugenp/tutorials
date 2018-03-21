package com.baeldung.injectiontypes;

import org.springframework.stereotype.Component;

@Component
public class StudentDao {

    public Student save(final Student student) {

        return student;
    }

}
