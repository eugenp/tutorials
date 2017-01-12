package com.baeldung.dao;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.baeldung.entity.primarydb.User;

@Named(value = "userDaoImpl")
@Transactional
public class UserDaoImpl {

	@PersistenceContext(unitName = "primaryDbEntityManagerFactory")
	private EntityManager em;

	public User saveOrUpdate(User entity) {
		User managedEntity = null;
		if (entity.getId() == null) {
			em.persist(entity);
			managedEntity = entity;
		} else {
			managedEntity = em.merge(entity);
		}
		return managedEntity;
	}
}
