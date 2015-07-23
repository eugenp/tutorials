package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IChildDao;
import org.baeldung.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.persistence.model.Child;
import org.springframework.stereotype.Repository;

@Repository
public class ChildDao extends AbstractHibernateDao<Child>implements IChildDao {

    public ChildDao() {
        super();

        setClazz(Child.class);
    }

    // API

}
