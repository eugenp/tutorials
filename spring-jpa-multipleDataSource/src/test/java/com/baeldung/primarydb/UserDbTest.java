package com.baeldung.primarydb;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baeldung.common.BaseTestDb;
import com.baeldung.dao.UserDaoImpl;
import com.baeldung.entity.primarydb.User;

@Transactional(value="primaryDbTransactionManager")
@Rollback(false)
public class UserDbTest extends BaseTestDb {

	@Inject
	@Named("userDaoImpl")
	private UserDaoImpl userDao;

	@Test
	public void saveUser() {
		User user = createUser();
		user = userDao.saveOrUpdate(user);
		Assert.notNull(user.getId());
	}
}
