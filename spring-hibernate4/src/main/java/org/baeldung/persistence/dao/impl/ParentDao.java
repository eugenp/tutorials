package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IParentDao;
import org.baeldung.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.persistence.model.Parent;
import org.springframework.stereotype.Repository;

@Repository
public class ParentDao extends AbstractHibernateDao<Parent>implements IParentDao {

    public ParentDao() {
        super();

        setClazz(Parent.class);
    }

    // API

}
