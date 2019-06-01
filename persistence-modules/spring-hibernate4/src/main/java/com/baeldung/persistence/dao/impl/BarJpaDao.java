package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.dao.common.AbstractJpaDao;
import com.baeldung.persistence.model.Bar;
import org.springframework.stereotype.Repository;

@Repository
public class BarJpaDao extends AbstractJpaDao<Bar> implements IBarDao {

    public BarJpaDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
