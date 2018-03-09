package org.springframework.tutorial.tutorial4.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
/*@Scope("singleton")*/
@Component
public class Address {

	private String city = "Chengdu";
	public Address() {
		System.out.println("My city:" + city);
	}
        public String getCity() {
		return city;
	}
	
}
