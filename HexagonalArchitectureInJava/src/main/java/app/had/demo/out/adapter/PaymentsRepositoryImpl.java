package app.had.demo.out.adapter;

import org.springframework.stereotype.Repository;

import app.had.demo.domain.TripDetails;
import app.had.demo.out.port.PaymentsRepository;

@Repository
public class PaymentsRepositoryImpl implements PaymentsRepository{

	@Override
	public void processPayment(TripDetails tripDetails) {
		
	}

}
