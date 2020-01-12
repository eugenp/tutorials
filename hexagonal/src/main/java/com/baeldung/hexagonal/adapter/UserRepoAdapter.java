package com.baeldung.hexagonal.adapter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.port.UserRepoPort;

@Repository
public class UserRepoAdapter implements UserRepoPort {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void createUser(User user) {
		entityManager.persist(user);
	}

	@Override
	public User getUser(Long userId) {
		return entityManager.find(User.class, userId);
	}
}
