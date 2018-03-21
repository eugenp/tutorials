package com.baeldung.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceWithSetterInjection {

    private StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {

        this.studentDao = studentDao;
    }

    public String save(final Student student) {

        return this.studentDao.save(student).getName();
    }

}
