package com.baeldung.springmodulith.application.events.rewards;

import com.baeldung.springmodulith.application.events.orders.OrderCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyPointsService {

	public static final int ORDER_COMPLETED_POINTS = 10;
	public static final int SING_UP_POINTS = 50;
	private final LoyalCustomersRepository loyalCustomers;

	public LoyaltyPointsService(LoyalCustomersRepository loyalCustomers) {
		this.loyalCustomers = loyalCustomers;
	}

	@EventListener
	public void onOrderCompleted(OrderCompletedEvent event) {
		if (loyalCustomers.find(event.customerId()).isEmpty()) {
			loyalCustomers.save(event.customerId());
			loyalCustomers.awardPoints(event.customerId(), SING_UP_POINTS);
		}

		loyalCustomers.awardPoints(event.customerId(), ORDER_COMPLETED_POINTS);
	}

}
