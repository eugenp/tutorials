package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IBarDao;
import org.baeldung.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.persistence.model.Bar;
import org.springframework.stereotype.Repository;

@Repository
public class BarDao extends AbstractHibernateDao<Bar> implements IBarDao {

    public BarDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
