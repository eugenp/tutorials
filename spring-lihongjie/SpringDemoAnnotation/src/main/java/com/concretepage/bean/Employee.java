package com.concretepage.bean;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
@Component
public class Employee {
	@Resource
	private Company company;
	@Resource
	private Address address;
	public Company getCompany() {
		return company;
	}
	public Address getAddress() {
		return address;
	}
}
