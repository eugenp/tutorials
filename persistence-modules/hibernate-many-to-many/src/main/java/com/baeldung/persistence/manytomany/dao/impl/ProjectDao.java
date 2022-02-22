package com.baeldung.persistence.manytomany.dao.impl;

import org.springframework.stereotype.Repository;
import com.baeldung.hibernate.manytomany.model.Project;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.manytomany.dao.IProjectDao;


@Repository
public class ProjectDao extends AbstractHibernateDao<Project> implements IProjectDao {

    public ProjectDao() {
        super();

        setClazz(Project.class);
    }
}
