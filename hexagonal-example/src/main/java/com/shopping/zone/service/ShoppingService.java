package com.shopping.zone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopping.zone.pojo.User;

@Service
public class ShoppingService {

	@Autowired
	ShoppingDao shoppingDao;

	public void registerUser(User user) {
		shoppingDao.registerUser(user);
	}

}
