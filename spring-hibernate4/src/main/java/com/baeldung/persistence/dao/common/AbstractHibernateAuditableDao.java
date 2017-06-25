package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;

@SuppressWarnings("unchecked")
public class AbstractHibernateAuditableDao<T extends Serializable> extends AbstractHibernateDao<T> implements IAuditOperations<T> {

    @Override
    public List<T> getEntitiesAtRevision(final Number revision) {
        final AuditReader auditReader = AuditReaderFactory.get(getCurrentSession());
        final AuditQuery query = auditReader.createQuery().forEntitiesAtRevision(clazz, revision);
        final List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<T> getEntitiesModifiedAtRevision(final Number revision) {
        final AuditReader auditReader = AuditReaderFactory.get(getCurrentSession());
        final AuditQuery query = auditReader.createQuery().forEntitiesModifiedAtRevision(clazz, revision);
        final List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<T> getRevisions() {
        final AuditReader auditReader = AuditReaderFactory.get(getCurrentSession());
        final AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(clazz, true, true);
        final List<T> resultList = query.getResultList();
        return resultList;
    }

}
