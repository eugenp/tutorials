package com.baeldung.spring.modulith.application.events;

import com.baeldung.spring.modulith.application.events.orders.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({ "modulith", "h2" })
class EventPublisherUnitTest {

	@Autowired
	OrderService orderService;

	@Autowired
	TestEventListener testEventListener;

	@BeforeEach
	void beforeEach() {
		testEventListener.reset();
	}

	@Test
	void whenPlacingOrder_thenPublishApplicationEvent() {
		orderService.placeOrder("customer1", "product1", "product2");

		assertThat(testEventListener.getEvents())
		  .hasSize(1).first()
		  .hasFieldOrPropertyWithValue("customerId", "customer1")
		  .hasFieldOrProperty("orderId")
		  .hasFieldOrProperty("timestamp");
	}

}
