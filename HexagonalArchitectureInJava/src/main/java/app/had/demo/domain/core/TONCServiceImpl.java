package app.had.demo.domain.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.had.demo.domain.Taxi;
import app.had.demo.domain.TripDetails;
import app.had.demo.in.port.TONCService;
import app.had.demo.out.port.DBRepository;
import app.had.demo.out.port.GPSRepository;
import app.had.demo.out.port.PaymentsRepository;

@Service
public class TONCServiceImpl implements TONCService{
	
	@Autowired
	private DBRepository dbRepository;
	
	@Autowired
	private GPSRepository gpsRepository;
	
	@Autowired
	private PaymentsRepository paymentsRepository;

	@Override
	public List<Taxi> getTaxies(String pickupAddress, String dropAddress) {
		return gpsRepository.getTaxies(pickupAddress, dropAddress);
	}

	@Override
	public TripDetails bookTaxi(TripDetails tripDetails) {
		return dbRepository.bookTaxi(tripDetails);
	}

	@Override
	public TripDetails startTrip(TripDetails tripDetails) {
		return dbRepository.startTrip(tripDetails);
	}

	@Override
	public TripDetails endTrip(TripDetails tripDetails) {
		paymentsRepository.processPayment(tripDetails);
		return dbRepository.endTrip(tripDetails);
	}

}
