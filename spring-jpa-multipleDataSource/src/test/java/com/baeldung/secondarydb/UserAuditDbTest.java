package com.baeldung.secondarydb;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baeldung.common.BaseTestDb;
import com.baeldung.dao.UserAuditDaoImpl;
import com.baeldung.dao.UserDaoImpl;
import com.baeldung.entity.primarydb.User;
import com.baeldung.entity.secondarydb.UserAudit;

@Transactional(value = "secondaryDbTransactionManager")
@Rollback(false)
public class UserAuditDbTest extends BaseTestDb {

	@Inject
	@Named("userAuditDaoImpl")
	private UserAuditDaoImpl userAuditDao;

	@Inject
	@Named("userDaoImpl")
	private UserDaoImpl userDao;

	@Test
	public void saveUser() {
		User user = createUser();
		user = userDao.saveOrUpdate(user);
		UserAudit userAudit = saveUserAudit(user.getId());
		Assert.notNull(userAudit.getId());
	}

	@Test
	public void saveUserAudit() {
		UserAudit userAudit = new UserAudit();
		userAudit.setUserAction("New record");
		userAudit.setUserId(null);
		userAudit = userAuditDao.saveOrUpdate(userAudit);
		Assert.notNull(userAudit.getId());
	}

	private UserAudit saveUserAudit(Long userId) {
		UserAudit userAudit = new UserAudit();
		userAudit.setUserAction("New record");
		userAudit.setUserId(userId);
		userAudit = userAuditDao.saveOrUpdate(userAudit);
		return userAudit;
	}
}
