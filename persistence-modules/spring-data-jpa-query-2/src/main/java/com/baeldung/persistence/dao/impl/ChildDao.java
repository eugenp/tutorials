package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Child;
import com.baeldung.persistence.dao.IChildDao;
import org.springframework.stereotype.Repository;

@Repository
public class ChildDao extends AbstractHibernateDao<Child> implements IChildDao {

    public ChildDao() {
        super();

        setClazz(Child.class);
    }

    // API

}
