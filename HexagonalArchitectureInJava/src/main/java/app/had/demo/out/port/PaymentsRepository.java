package app.had.demo.out.port;

import app.had.demo.domain.TripDetails;

public interface PaymentsRepository {
	void processPayment(TripDetails tripDetails);
}
