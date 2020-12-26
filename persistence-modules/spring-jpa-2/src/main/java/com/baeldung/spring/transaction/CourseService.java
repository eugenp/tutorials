package com.baeldung.spring.transaction;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class CourseService {
    
    @Autowired
    private CourseDao courseDao;
    
    @Transactional
    public void createCourseDeclarativeWithRuntimeException(Course course) {
        courseDao.createWithRuntimeException(course);
    }
    
    @Transactional(rollbackFor = { SQLException.class })
    public void createCourseDeclarativeWithCheckedException(Course course) throws SQLException {
        courseDao.createWithCheckedException(course);
    }
    
    public void createCourseDefaultRatingProgramatic(Course course) {
        try {
            courseDao.createWithRuntimeException(course);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    
}
