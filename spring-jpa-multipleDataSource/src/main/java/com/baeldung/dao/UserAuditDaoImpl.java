package com.baeldung.dao;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.baeldung.entity.secondarydb.UserAudit;

@Named(value = "userAuditDaoImpl")
@Transactional
public class UserAuditDaoImpl {

	@PersistenceContext(unitName = "secondaryDbEntityManagerFactory")
	private EntityManager em;

	public UserAudit saveOrUpdate(UserAudit entity) {
		UserAudit managedEntity = null;
		if (entity.getId() == null) {
			em.persist(entity);
			managedEntity = entity;
		} else {
			managedEntity = em.merge(entity);
		}
		return managedEntity;
	}
}
