package com.concretepage.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value="session", proxyMode =ScopedProxyMode.TARGET_CLASS)
@Component
public class Address {
	private String city = "Varanasi";
	public Address() {
		System.out.println("My city:" + city);
	}
    public String getCity() {
		return city;
	}
}