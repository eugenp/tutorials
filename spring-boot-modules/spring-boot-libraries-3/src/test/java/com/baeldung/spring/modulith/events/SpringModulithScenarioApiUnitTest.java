package com.baeldung.spring.modulith.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.spring.modulith.events.orders.OrderCompletedEvent;
import com.baeldung.spring.modulith.events.orders.OrderService;
import com.baeldung.spring.modulith.events.rewards.LoyalCustomersRepository;

@ApplicationModuleTest
@ActiveProfiles({ "modulith", "h2" })
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
