package com.baeldung.spring.transaction;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class CourseService {
    
    @Autowired
    private CourseDao courseDao;
    
    @Transactional
    public void createCourseDeclarativeWithRuntimeException(Course course) {
        courseDao.create(course);
        throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
    }
    
    @Transactional(rollbackFor = { SQLException.class })
    public void createCourseDeclarativeWithCheckedException(Course course) throws SQLException {
        courseDao.create(course);
        throw new SQLException("Throwing exception for demoing Rollback!!!");
    }
    
    public void createCourseDefaultRatingProgramatic(Course course) {
        try {
            courseDao.create(course);
            throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    
    @Transactional(noRollbackFor = { SQLException.class })
    public void createCourseDeclarativeWithNoRollBack(Course course) throws SQLException {
        courseDao.create(course);
        throw new SQLException("Throwing exception for demoing Rollback!!!");
    }
    
}
