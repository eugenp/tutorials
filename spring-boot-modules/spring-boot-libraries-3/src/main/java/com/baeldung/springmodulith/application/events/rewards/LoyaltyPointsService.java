package com.baeldung.springmodulith.application.events.rewards;

import com.baeldung.springmodulith.application.events.orders.OrderCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyPointsService {

	public static final int ORDER_COMPLETED_POINTS = 60;
	private final LoyalCustomersRepository loyalCustomers;

	public LoyaltyPointsService(LoyalCustomersRepository loyalCustomers) {
		this.loyalCustomers = loyalCustomers;
	}

	@EventListener
	public void onOrderCompleted(OrderCompletedEvent event) {
		// business logic to award points to loyal customers
		loyalCustomers.awardPoints(event.customerId(), ORDER_COMPLETED_POINTS);
	}

}
