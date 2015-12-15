package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IBarDao;
import org.baeldung.persistence.dao.common.AbstractJpaDao;
import org.baeldung.persistence.model.Bar;
import org.springframework.stereotype.Repository;

@Repository
public class BarJpaDao extends AbstractJpaDao<Bar> implements IBarDao {

    public BarJpaDao() {
        super();

        setClazz(Bar.class);
    }

    // API

}
