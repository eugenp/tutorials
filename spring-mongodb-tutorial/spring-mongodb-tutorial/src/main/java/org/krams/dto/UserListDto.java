package org.krams.dto;

import java.util.List;

import org.krams.domain.User;

public class UserListDto {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
