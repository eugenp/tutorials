package com.baeldung.jpa.schemageneration.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jpa.schemageneration.model.AccountSetting;

public interface AccountSettingRepository extends CrudRepository<AccountSetting, Long> {

    AccountSetting findByAccountId(Long accountId);
}
