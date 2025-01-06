package com.baeldung.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.persistence.dao.IChildDao;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Child;

@Repository
public class ChildDao extends AbstractHibernateDao<Child> implements IChildDao {

    public ChildDao() {
        super();

        setClazz(Child.class);
    }

    // API

}
