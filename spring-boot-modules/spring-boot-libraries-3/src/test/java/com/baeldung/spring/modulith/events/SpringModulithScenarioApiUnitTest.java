package com.baeldung.spring.modulith.events;

import com.baeldung.spring.modulith.events.orders.OrderCompletedEvent;
import com.baeldung.spring.modulith.events.orders.OrderService;
import com.baeldung.spring.modulith.events.rewards.LoyalCustomersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.ApplicationModuleTest.BootstrapMode;
import org.springframework.modulith.test.Scenario;

import java.time.Duration;
import java.time.Instant;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
class SpringModulithScenarioApiUnitTest {

	@Autowired
	OrderService orderService;

	@Autowired
	LoyalCustomersRepository loyalCustomers;

	@Test
	void whenPlacingOrder_thenPublishOrderCompletedEvent(Scenario scenario) {
		scenario.stimulate(() -> orderService.placeOrder("customer-1", "product-1", "product-2"))
		  .andWaitForEventOfType(OrderCompletedEvent.class)
		  .toArriveAndVerify(evt -> assertThat(evt)
			.hasFieldOrPropertyWithValue("customerId", "customer-1")
			.hasFieldOrProperty("orderId")
			.hasFieldOrProperty("timestamp"));
	}

	@Test
	void whenReceivingPublishOrderCompletedEvent_thenRewardCustomerWithLoyaltyPoints(Scenario scenario) {
		scenario.publish(new OrderCompletedEvent("order-1", "customer-1", Instant.now()))
		  .andWaitForStateChange(() -> loyalCustomers.find("customer-1"))
		  .andVerify(it -> assertThat(it)
		    .isPresent().get()
		    .hasFieldOrPropertyWithValue("customerId", "customer-1")
		    .hasFieldOrPropertyWithValue("points", 60));
	}
}
