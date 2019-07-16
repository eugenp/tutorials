package com.shopping.zone.dao;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import com.shopping.zone.pojo.User;
import com.shopping.zone.service.ShoppingDao;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ShoppingDaoImpl implements ShoppingDao {

	@PersistenceContext
	EntityManager entityManager;

	public User registerUser(User user) {
		entityManager.persist(user);
		return user;
	}

}
