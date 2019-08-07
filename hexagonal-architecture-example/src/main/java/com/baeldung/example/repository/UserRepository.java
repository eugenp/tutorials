package com.baeldung.example.repository;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.example.model.User;

@Repository
public class UserRepository {

	private static List<User> userList = new ArrayList<>();
	
	public User getUserByUserId(int userId) {
		List<User> list = userList.parallelStream().filter(u -> u.getUserId() == userId).collect(Collectors.toList());
		if(!list.isEmpty()) {
			return list.parallelStream().findAny().get();
		} else {
			return new User();
		}
	}
	
	public User createNewUser(User user) {
		int id = userList.parallelStream().max(Comparator.comparing(u -> u.getUserId())).get().getUserId();
		user.setUserId(++id);
		userList.add(user);
		return user;
	}
	
}
