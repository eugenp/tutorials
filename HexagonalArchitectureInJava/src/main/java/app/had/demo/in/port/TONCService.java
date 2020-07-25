package app.had.demo.in.port;

import java.util.List;

import app.had.demo.domain.Taxi;
import app.had.demo.domain.TripDetails;

public interface TONCService {
	List<Taxi> getTaxies(String pickupAddress, String dropAddress);
	
	TripDetails bookTaxi(TripDetails tripDetails);
	
	TripDetails startTrip(TripDetails tripDetails);
	
	TripDetails endTrip(TripDetails tripDetails);
}
