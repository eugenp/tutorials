package com.chr.service;

import java.util.List;

import com.chr.domain.User;

/**
 * @author Edwin Chen
 *
 */
public interface UserOperationsService {
	void add(User user);
	User getUser(String key);
	
}
