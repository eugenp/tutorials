package com.baeldung.port.outbound.impl;

import com.baeldung.port.outbound.IBarDao;
import com.baeldung.port.outbound.common.AbstractJpaDao;
import com.baeldung.port.model.Bar;
import org.springframework.stereotype.Repository;

@Repository
public class BarJpaDao extends AbstractJpaDao<Bar> implements IBarDao {

    public BarJpaDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
