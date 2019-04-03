package com.baeldung.persistence.repo;
/*
 * created by pareshP on 02/04/19
 */

import com.baeldung.persistence.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
}
