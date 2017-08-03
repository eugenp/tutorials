package com.baeldung.hibernate.manyToMany.usage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hibernate.manyToMany.model.Project;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.service.common.AbstractHibernateService;


@Service
public class ProjectService extends AbstractHibernateService<Project> implements IProjectService {
   
    @Autowired
    private IProjectDao dao;

    public ProjectService() {
        super();
    }

    // API

    @Override
    protected IOperations<Project> getDao() {
        return dao;
    }
}
