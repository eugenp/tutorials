package com.baeldung.dao;

import com.baeldung.entity.secondarydb.UserAudit;

public interface UserAuditDao {

	public UserAudit saveUserAudit(UserAudit userAudit);
}
