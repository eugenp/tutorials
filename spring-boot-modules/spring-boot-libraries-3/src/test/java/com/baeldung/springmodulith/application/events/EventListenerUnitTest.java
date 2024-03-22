package com.baeldung.springmodulith.application.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.springmodulith.application.events.orders.OrderCompletedEvent;
import com.baeldung.springmodulith.application.events.rewards.LoyalCustomersRepository;

@SpringBootTest
@ComponentScan(basePackages = "com.baeldung.springmodulith.application.events")
class EventListenerUnitTest {

	@Autowired
	private LoyalCustomersRepository customers;

	@Autowired
	private ApplicationEventPublisher testEventPublisher;

	@Test
	void whenPublishingOrderCompletedEvent_thenRewardCustomerWithLoyaltyPoints() {
		OrderCompletedEvent event = new OrderCompletedEvent("order-1", "customer-1", Instant.now());

		testEventPublisher.publishEvent(event);

		assertThat(customers.find("customer-1"))
		  .isPresent().get()
		  .hasFieldOrPropertyWithValue("customerId", "customer-1")
		  .hasFieldOrPropertyWithValue("points", 60);
	}

}
