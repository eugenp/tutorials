package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.model.Bar;
import org.springframework.stereotype.Repository;

@Repository
public class BarDao extends AbstractHibernateDao<Bar> implements IBarDao {

    public BarDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
