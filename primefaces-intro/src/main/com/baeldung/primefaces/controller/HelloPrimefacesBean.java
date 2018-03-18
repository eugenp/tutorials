package com.baeldung.primefaces.controller;

import com.baeldung.primefaces.model.User;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "helloPrimefacesBean")
public class HelloPrimefacesBean {
	
	private User user = new User("prime", "baeldung");

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
