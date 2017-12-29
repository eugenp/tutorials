package com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Address {
	private Locality locality;
	private State state;
	private City city;

	public Address() {
	}

	@Autowired
	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	@Autowired
	public void setState(State state) {
		this.state = state;
	}

	@Autowired
	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return String.format("Locality: %s City: %s State: %s", locality, city, state);
	}
}
