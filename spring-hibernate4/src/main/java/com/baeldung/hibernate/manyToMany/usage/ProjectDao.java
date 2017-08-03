package com.baeldung.hibernate.manyToMany.usage;

import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.manyToMany.model.Project;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;


@Repository
public class ProjectDao extends AbstractHibernateDao<Project> implements IProjectDao {

    public ProjectDao() {
        super();

        setClazz(Project.class);
    }
}
