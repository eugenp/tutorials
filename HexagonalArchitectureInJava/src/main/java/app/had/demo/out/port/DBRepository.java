package app.had.demo.out.port;

import app.had.demo.domain.TripDetails;

public interface DBRepository {
	
	TripDetails bookTaxi(TripDetails tripDetails);
	
	TripDetails startTrip(TripDetails tripDetails);
	
	TripDetails endTrip(TripDetails tripDetails);
}
