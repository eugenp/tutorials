package com.baeldung.examples.olingo2;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ODataJPAServiceFactory implementation for our sample domain 
 * @author Philippe
 *
 */
@Component
public class CarsODataJPAServiceFactory extends ODataJPAServiceFactory {

    private static final Logger log = LoggerFactory.getLogger(CarsODataJPAServiceFactory.class);

    public CarsODataJPAServiceFactory() {
        // Enable detailed error messages (useful for debugging)
        setDetailErrors(true);
    }

    /**
     * This method will be called by Olingo on every request to
     * initialize the ODataJPAContext that will be used. 
     */
    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {

        log.info("[I32] >>> initializeODataJPAContext()");
        ODataJPAContext ctx = getODataJPAContext();
        ODataContext octx = ctx.getODataContext();
        HttpServletRequest request = (HttpServletRequest)octx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
        EntityManager em = (EntityManager)request.getAttribute(JerseyConfig.EntityManagerFilter.EM_REQUEST_ATTRIBUTE);
                
        // Here we're passing the EM that was created by the EntityManagerFilter (see JerseyConfig)
        ctx.setEntityManager(new EntityManagerWrapper(em));
        ctx.setPersistenceUnitName("default");
        
        // We're managing the EM's lifecycle, so we must inform Olingo that it should not
        // try to manage transactions and/or persistence sessions
        ctx.setContainerManaged(true);                
        return ctx;
    }

    static class EntityManagerWrapper implements EntityManager {

        private EntityManager delegate;

        public void persist(Object entity) {
            log.info("[I68] persist: entity.class=" + entity.getClass()
                .getSimpleName());
            delegate.persist(entity);
            // delegate.flush();
        }

        public <T> T merge(T entity) {
            log.info("[I74] merge: entity.class=" + entity.getClass()
                .getSimpleName());
            return delegate.merge(entity);
        }

        public void remove(Object entity) {
            log.info("[I78] remove: entity.class=" + entity.getClass()
                .getSimpleName());
            delegate.remove(entity);
        }

        public <T> T find(Class<T> entityClass, Object primaryKey) {
            return delegate.find(entityClass, primaryKey);
        }

        public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
            return delegate.find(entityClass, primaryKey, properties);
        }

        public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
            return delegate.find(entityClass, primaryKey, lockMode);
        }

        public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
            return delegate.find(entityClass, primaryKey, lockMode, properties);
        }

        public <T> T getReference(Class<T> entityClass, Object primaryKey) {
            return delegate.getReference(entityClass, primaryKey);
        }

        public void flush() {
            delegate.flush();
        }

        public void setFlushMode(FlushModeType flushMode) {
            delegate.setFlushMode(flushMode);
        }

        public FlushModeType getFlushMode() {
            return delegate.getFlushMode();
        }

        public void lock(Object entity, LockModeType lockMode) {
            delegate.lock(entity, lockMode);
        }

        public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
            delegate.lock(entity, lockMode, properties);
        }

        public void refresh(Object entity) {
            delegate.refresh(entity);
        }

        public void refresh(Object entity, Map<String, Object> properties) {
            delegate.refresh(entity, properties);
        }

        public void refresh(Object entity, LockModeType lockMode) {
            delegate.refresh(entity, lockMode);
        }

        public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
            delegate.refresh(entity, lockMode, properties);
        }

        public void clear() {
            delegate.clear();
        }

        public void detach(Object entity) {
            delegate.detach(entity);
        }

        public boolean contains(Object entity) {
            return delegate.contains(entity);
        }

        public LockModeType getLockMode(Object entity) {
            return delegate.getLockMode(entity);
        }

        public void setProperty(String propertyName, Object value) {
            delegate.setProperty(propertyName, value);
        }

        public Map<String, Object> getProperties() {
            return delegate.getProperties();
        }

        public Query createQuery(String qlString) {
            return delegate.createQuery(qlString);
        }

        public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
            return delegate.createQuery(criteriaQuery);
        }

        public Query createQuery(CriteriaUpdate updateQuery) {
            return delegate.createQuery(updateQuery);
        }

        public Query createQuery(CriteriaDelete deleteQuery) {
            return delegate.createQuery(deleteQuery);
        }

        public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
            return delegate.createQuery(qlString, resultClass);
        }

        public Query createNamedQuery(String name) {
            return delegate.createNamedQuery(name);
        }

        public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
            return delegate.createNamedQuery(name, resultClass);
        }

        public Query createNativeQuery(String sqlString) {
            return delegate.createNativeQuery(sqlString);
        }

        public Query createNativeQuery(String sqlString, Class resultClass) {
            return delegate.createNativeQuery(sqlString, resultClass);
        }

        public Query createNativeQuery(String sqlString, String resultSetMapping) {
            return delegate.createNativeQuery(sqlString, resultSetMapping);
        }

        public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
            return delegate.createNamedStoredProcedureQuery(name);
        }

        public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
            return delegate.createStoredProcedureQuery(procedureName);
        }

        public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
            return delegate.createStoredProcedureQuery(procedureName, resultClasses);
        }

        public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
            return delegate.createStoredProcedureQuery(procedureName, resultSetMappings);
        }

        public void joinTransaction() {
            delegate.joinTransaction();
        }

        public boolean isJoinedToTransaction() {
            return delegate.isJoinedToTransaction();
        }

        public <T> T unwrap(Class<T> cls) {
            return delegate.unwrap(cls);
        }

        public Object getDelegate() {
            return delegate.getDelegate();
        }

        public void close() {
            log.info("[I229] close");
            delegate.close();
        }

        public boolean isOpen() {
            boolean isOpen = delegate.isOpen();
            log.info("[I236] isOpen: " + isOpen);
            return isOpen;
        }

        public EntityTransaction getTransaction() {
            log.info("[I240] getTransaction()");
            return delegate.getTransaction();
        }

        public EntityManagerFactory getEntityManagerFactory() {
            return delegate.getEntityManagerFactory();
        }

        public CriteriaBuilder getCriteriaBuilder() {
            return delegate.getCriteriaBuilder();
        }

        public Metamodel getMetamodel() {
            return delegate.getMetamodel();
        }

        public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
            return delegate.createEntityGraph(rootType);
        }

        public EntityGraph<?> createEntityGraph(String graphName) {
            return delegate.createEntityGraph(graphName);
        }

        public EntityGraph<?> getEntityGraph(String graphName) {
            return delegate.getEntityGraph(graphName);
        }

        public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
            return delegate.getEntityGraphs(entityClass);
        }

        public EntityManagerWrapper(EntityManager delegate) {
            this.delegate = delegate;
        }

    }

}
