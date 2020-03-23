package com.baeldung.port.manytomany.dao.impl;

import org.springframework.stereotype.Repository;
import com.baeldung.hibernate.manytomany.model.Project;
import com.baeldung.port.dao.common.AbstractHibernateDao;
import com.baeldung.port.manytomany.dao.IProjectDao;


@Repository
public class ProjectDao extends AbstractHibernateDao<Project> implements IProjectDao {

    public ProjectDao() {
        super();

        setClazz(Project.class);
    }
}
