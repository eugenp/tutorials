package com.baeldung.springmodulith.application.events;

import com.baeldung.springmodulith.application.events.orders.OrderService;
import com.baeldung.springmodulith.application.events.rewards.LoyalCustomersRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
@ComponentScan(basePackages = "com.baeldung.springmodulith.application.events")
public class SpringModulithScenarioApiUnitTest {

	@Autowired
	OrderService orderService;

	@Autowired
	LoyalCustomersRepository loyalCustomers;

	@Test
	void test(Scenario scenario) {

	}

}
