package com.baeldung.springmodulith.application.events;

import com.baeldung.springmodulith.application.events.orders.OrderService;
import com.baeldung.springmodulith.application.events.rewards.LoyalCustomersRepository;
import com.baeldung.springmodulith.application.events.rewards.LoyalCustomersRepository.LoyalCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ComponentScan(basePackages = "com.baeldung.springmodulith.application.events")
public class ApplicationEventsUnitTest {

	@Autowired
	OrderService orderService;

	@Autowired
	LoyalCustomersRepository loyalCustomers;

	@Test
	void whenNewCustomerCompletesAnOrder_thenHeReceivesSingUpPointsPlusOrderPoints() {
		orderService.placeOrder("customer1", "product1", "product2");

		Optional<LoyalCustomer> customer = loyalCustomers.find("customer1");

		assertThat(customer).isPresent()
				.get()
				.extracting(LoyalCustomer::points)
				.isEqualTo(50 + 10);
	}

}
