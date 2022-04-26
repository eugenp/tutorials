package com.baeldung.manytomany.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.manytomany.dao.IProjectDao;
import com.baeldung.manytomany.dao.common.AbstractHibernateDao;
import com.baeldung.manytomany.model.Project;


@Repository
public class ProjectDao extends AbstractHibernateDao<Project> implements IProjectDao {

    public ProjectDao() {
        super();

        setClazz(Project.class);
    }
}
