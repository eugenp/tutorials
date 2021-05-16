package com.baeldung.spring.transaction;

import org.springframework.stereotype.Repository;

import com.baeldung.spring.hibernate.AbstractHibernateDao;

@Repository
public class CourseDao extends AbstractHibernateDao<Course> {
    public CourseDao() {
        super();
        setClazz(Course.class);
    }
}
