package com.baeldung.port.outbound.impl;

import java.util.List;

import com.baeldung.port.outbound.IBarAuditableDao;
import com.baeldung.port.outbound.common.AbstractHibernateAuditableDao;
import com.baeldung.port.model.Bar;

public class BarAuditableDao extends AbstractHibernateAuditableDao<Bar> implements IBarAuditableDao {

    public BarAuditableDao() {
        super();

        setClazz(Bar.class);
    }

    // API

    @Override
    public List<Bar> getRevisions() {
        final List<Bar> resultList = super.getRevisions();
        for (final Bar bar : resultList) {
            bar.getFooSet().size(); // force FooSet initialization
        }
        return resultList;
    }

}