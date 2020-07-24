package app.had.demo.out.port;

import java.util.List;

import app.had.demo.domain.Taxi;

public interface GPSRepository {
	List<Taxi> getTaxies(String pickUpAddress, String dropAddress);
}
