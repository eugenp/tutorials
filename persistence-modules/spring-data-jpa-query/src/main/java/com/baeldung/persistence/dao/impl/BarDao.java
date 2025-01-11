package com.baeldung.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Bar;

@Repository
public class BarDao extends AbstractHibernateDao<Bar> implements IBarDao {

    public BarDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
