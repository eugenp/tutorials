package com.baeldung.dddhexagonalspring.domain.repository;

import com.baeldung.dddhexagonalspring.domain.Organization;

public interface OrganizationRepository {
	void create(String title);

	Organization load(Long orgId);
}
