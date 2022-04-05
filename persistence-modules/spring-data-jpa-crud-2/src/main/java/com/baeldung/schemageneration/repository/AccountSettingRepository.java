package com.baeldung.schemageneration.repository;

import com.baeldung.schemageneration.model.AccountSetting;
import org.springframework.data.repository.CrudRepository;

public interface AccountSettingRepository extends CrudRepository<AccountSetting, Long> {
    AccountSetting findByAccountId(Long accountId);
}
