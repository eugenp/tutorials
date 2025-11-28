package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.envers.AuditReader;
// FIX: AuditQueryCreator replaces the query building methods on AuditReader
import org.hibernate.envers.query.AuditQueryCreator; 
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class AbstractHibernateAuditableDao<T extends Serializable> extends AbstractHibernateDao<T> implements IAuditOperations<T> {

    // FIX 1: Inject AuditReader instead of getting it from static factory
    @Autowired
    private AuditReader auditReader;

    private AuditQueryCreator createQuery() {
        return auditReader.createQuery();
    }

    @Override
    public List<T> getEntitiesAtRevision(final Number revision) {
        // FIX 2: Use createQuery().forEntitiesAtRevision
        final List<T> resultList = createQuery()
            .forEntitiesAtRevision(clazz, revision)
            .getResultList();
        return resultList;
    }

    @Override
    public List<T> getEntitiesModifiedAtRevision(final Number revision) {
        // FIX 2: Use createQuery().forEntitiesModifiedAtRevision
        final List<T> resultList = createQuery()
            .forEntitiesModifiedAtRevision(clazz, revision)
            .getResultList();
        return resultList;
    }

    @Override
    public List<T> getRevisions() {
        // FIX 2: Use createQuery().forRevisionsOfEntity
        final List<T> resultList = createQuery()
            .forRevisionsOfEntity(clazz, true, true)
            .getResultList();
        return resultList;
    }

}
