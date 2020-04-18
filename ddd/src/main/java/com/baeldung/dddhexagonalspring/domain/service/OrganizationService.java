package com.baeldung.dddhexagonalspring.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.dddhexagonalspring.domain.Organization;
import com.baeldung.dddhexagonalspring.domain.repository.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository orgRepository;

	public void create(String title) {
		orgRepository.create(title);
	}

	public Organization load(Long orgId) {
		return orgRepository.load(orgId);
	}

}
