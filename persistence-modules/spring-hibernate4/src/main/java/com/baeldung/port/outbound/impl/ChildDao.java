package com.baeldung.port.outbound.impl;

import com.baeldung.port.outbound.common.AbstractHibernateDao;
import com.baeldung.port.model.Child;
import com.baeldung.port.outbound.IChildDao;
import org.springframework.stereotype.Repository;

@Repository
public class ChildDao extends AbstractHibernateDao<Child> implements IChildDao {

    public ChildDao() {
        super();

        setClazz(Child.class);
    }

    // API

}
