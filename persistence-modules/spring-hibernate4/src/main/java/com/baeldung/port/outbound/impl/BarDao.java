package com.baeldung.port.outbound.impl;

import com.baeldung.port.outbound.common.AbstractHibernateDao;
import com.baeldung.port.outbound.IBarDao;
import com.baeldung.port.model.Bar;
import org.springframework.stereotype.Repository;

@Repository
public class BarDao extends AbstractHibernateDao<Bar> implements IBarDao {

    public BarDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
