package com.concretepage.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Teacher {
	@Autowired
	private Address teachAddress;
	public Address getTeachAddress() {
		return teachAddress;
	}
}