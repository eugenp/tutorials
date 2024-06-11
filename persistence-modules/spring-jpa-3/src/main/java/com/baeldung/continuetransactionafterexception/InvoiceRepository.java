package com.baeldung.continuetransactionafterexception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.continuetransactionafterexception.model.InvoiceEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class InvoiceRepository {
    private final Logger logger = LoggerFactory.getLogger(InvoiceRepository.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public void saveBatch(List<InvoiceEntity> invoiceEntities) {
        invoiceEntities.forEach(i -> entityManager.persist(i));
        try {
            entityManager.flush();
        } catch (Exception e) {
            logger.error("Duplicates detected, save individually", e);

            invoiceEntities.forEach(i -> {
                try {
                    save(i);
                } catch (Exception ex) {
                    logger.error("Problem saving individual entity {}", i.getSerialNumber(), ex);
                }
            });
        }
    }

    @Transactional
    public void save(InvoiceEntity invoiceEntity) {
        if (invoiceEntity.getId() == null) {
            entityManager.persist(invoiceEntity);
        } else {
            entityManager.merge(invoiceEntity);
        }

        entityManager.flush();
        logger.info("Entity is saved: {}", invoiceEntity.getSerialNumber());
    }

    public void saveBatchUsingManualTransaction(List<InvoiceEntity> testEntities) {
        EntityTransaction transaction = null;
        try (EntityManager em = em()) {
            transaction = em.getTransaction();
            transaction.begin();
            testEntities.forEach(em::persist);
            try {
                em.flush();
            } catch (Exception e) {
                logger.error("Duplicates detected, save individually", e);
                transaction.rollback();
                testEntities.forEach(t -> {
                    EntityTransaction newTransaction = em.getTransaction();
                    try {
                        newTransaction.begin();
                        saveUsingManualTransaction(t, em);
                    } catch (Exception ex) {
                        logger.error("Problem saving individual entity <{}>", t.getSerialNumber(), ex);
                        newTransaction.rollback();
                    } finally {
                        commitTransactionIfNeeded(newTransaction);
                    }
                });

            }
        } finally {
            commitTransactionIfNeeded(transaction);
        }
    }

    private void commitTransactionIfNeeded(EntityTransaction newTransaction) {
        if (newTransaction != null && newTransaction.isActive()) {
            if (!newTransaction.getRollbackOnly()) {
                newTransaction.commit();
            }
        }
    }

    private void saveUsingManualTransaction(InvoiceEntity invoiceEntity, EntityManager em) {
        if (invoiceEntity.getId() == null) {
            em.persist(invoiceEntity);
        } else {
            em.merge(invoiceEntity);
        }

        em.flush();
        logger.info("Entity is saved: {}", invoiceEntity.getSerialNumber());
    }

    private EntityManager em() {
        return entityManagerFactory.createEntityManager();
    }

    @Transactional
    public void saveBatchOnly(List<InvoiceEntity> testEntities) {
        testEntities.forEach(entityManager::persist);
        entityManager.flush();
    }

    public List<InvoiceEntity> findAll() {
        TypedQuery<InvoiceEntity> query = entityManager.createQuery("SELECT i From InvoiceEntity i", InvoiceEntity.class);
        return query.getResultList();
    }

    @Transactional
    public void deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM InvoiceEntity");
        query.executeUpdate();
    }
}
