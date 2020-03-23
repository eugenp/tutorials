package com.baeldung.port.outbound.impl;

import com.baeldung.port.outbound.IParentDao;
import com.baeldung.port.outbound.common.AbstractHibernateDao;
import com.baeldung.port.model.Parent;
import org.springframework.stereotype.Repository;

@Repository
public class ParentDao extends AbstractHibernateDao<Parent> implements IParentDao {

    public ParentDao() {
        super();

        setClazz(Parent.class);
    }

    // API

}
