package com.baeldung.spring.transaction;

import java.sql.SQLException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.hibernate.AbstractHibernateDao;

@Repository
public class CourseDao extends AbstractHibernateDao<Course> {
    public CourseDao() {
        super();
        setClazz(Course.class);
    }
    
    public Course createWithRuntimeException(final Course entity) {
        throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
    }
    
    public Course createWithCheckedException(final Course entity) throws SQLException {
        throw new SQLException("Throwing exception for demoing Rollback!!!");
    }
}
