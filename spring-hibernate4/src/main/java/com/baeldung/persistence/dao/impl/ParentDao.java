package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.dao.IParentDao;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Parent;
import org.springframework.stereotype.Repository;

@Repository
class ParentDao extends AbstractHibernateDao<Parent> implements IParentDao {

    public ParentDao() {
        super();

        setClazz(Parent.class);
    }

    // API

}
