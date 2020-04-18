package com.baeldung.dddhexagonalspring.domain.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.dddhexagonalspring.domain.Organization;

@Service
public class OrganizationRepositoryImpl implements OrganizationRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void create(String title) {
		Organization org = new Organization();
		org.setTitle(title);
		org.setCode(title);
		entityManager.persist(org);
	}

	@Override
	public Organization load(Long orgId) {
		return entityManager.find(Organization.class, orgId);
	}

}
