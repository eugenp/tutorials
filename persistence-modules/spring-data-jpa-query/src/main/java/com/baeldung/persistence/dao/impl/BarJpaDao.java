package com.baeldung.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.dao.common.AbstractJpaDao;
import com.baeldung.persistence.model.Bar;

@Repository
public class BarJpaDao extends AbstractJpaDao<Bar> implements IBarDao {

    public BarJpaDao() {
        super();

        setClazz(Bar.class);
    }

}
