package org.baeldung.spring.cloud.vaultsample.repository;

import org.baeldung.spring.cloud.vaultsample.domain.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="accounts", path="accounts")
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

}
