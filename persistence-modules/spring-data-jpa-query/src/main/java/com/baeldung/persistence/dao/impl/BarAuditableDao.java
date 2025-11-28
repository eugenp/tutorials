package com.baeldung.persistence.dao.impl;

import java.util.List;

import com.baeldung.persistence.dao.IBarAuditableDao;
import com.baeldung.persistence.dao.common.AbstractHibernateAuditableDao;
import com.baeldung.persistence.model.Bar;

public class BarAuditableDao extends AbstractHibernateAuditableDao<Bar> implements IBarAuditableDao {

    public BarAuditableDao() {
        super();

        // Sets the generic type for the AbstractHibernateAuditableDao
        setClazz(Bar.class);
    }

    // API

    /**
     * Overrides the default getRevisions to ensure the 'fooSet' (a lazy collection) 
     * is initialized before the audit result list is returned and the session is closed.
     */
    @Override
    public List<Bar> getRevisions() {
        final List<Bar> resultList = super.getRevisions();
        
        // This loop forces the initialization of the lazy-loaded collection (FooSet)
        // using the currently open Hibernate Session, preventing a LazyInitializationException 
        // when the list is accessed outside the transaction boundary.
        for (final Bar bar : resultList) {
            if (bar.getFooSet() != null) {
                bar.getFooSet().size(); // force FooSet initialization
            }
        }
        return resultList;
    }

}
