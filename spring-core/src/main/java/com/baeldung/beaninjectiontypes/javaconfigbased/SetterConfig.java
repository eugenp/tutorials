package com.baeldung.beaninjectiontypes.javaconfigbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain.Locality;
import com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain.City;
import com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain.State;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain")
public class SetterConfig {

	@Bean
	public Locality locality() {
		Locality locality = new Locality();
		locality.setHouseNo("B-8");
		locality.setRoad("Abc road");
		return locality;
	}

	@Bean
	public State state() {
		State state = new State();
		state.setState("Georgia");
		return state;
	}

	@Bean
	public City trailer() {
		City trailer = new City();
		return trailer;
	}
}