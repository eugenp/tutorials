package app.had.demo.out.adapter;

import org.springframework.stereotype.Repository;

import app.had.demo.domain.TripDetails;
import app.had.demo.out.port.DBRepository;

@Repository
public class DBRepositoryImpl implements DBRepository{
	
	@Override
	public TripDetails bookTaxi(TripDetails tripDetails) {
		tripDetails  = new TripDetails();
		tripDetails.setStatus("CONFIRM");
		return tripDetails;
	}

	@Override
	public TripDetails startTrip(TripDetails tripDetails) {
		tripDetails  = new TripDetails();
		tripDetails.setStatus("BEGIN");
		return tripDetails;
	}

	@Override
	public TripDetails endTrip(TripDetails tripDetails) {
		tripDetails  = new TripDetails();
		tripDetails.setStatus("COMPLETE");
		return tripDetails;
	}

}
