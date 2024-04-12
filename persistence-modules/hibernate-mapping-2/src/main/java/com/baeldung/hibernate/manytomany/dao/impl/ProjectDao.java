package com.baeldung.hibernate.manytomany.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.manytomany.dao.IProjectDao;
import com.baeldung.hibernate.manytomany.dao.common.AbstractHibernateDao;
import com.baeldung.hibernate.manytomany.model.Project;

@Repository
public class ProjectDao extends AbstractHibernateDao<Project> implements IProjectDao {

    public ProjectDao() {
        super();

        setClazz(Project.class);
    }
}
