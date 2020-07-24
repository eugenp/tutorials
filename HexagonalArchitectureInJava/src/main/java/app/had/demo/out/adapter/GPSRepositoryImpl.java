package app.had.demo.out.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import app.had.demo.domain.Taxi;
import app.had.demo.out.port.GPSRepository;

@Repository
public class GPSRepositoryImpl implements GPSRepository{

	@Override
	public List<Taxi> getTaxies(String pickUpAddress, String dropAddress) {
		List<Taxi> taxies = new ArrayList<>();
		
		Taxi tx1 = new Taxi();
		tx1.setTaxiId(101);
		taxies.add(tx1);
		
		Taxi tx2 = new Taxi();
		tx2.setTaxiId(102);
		taxies.add(tx2);
		
		Taxi tx3 = new Taxi();
		tx3.setTaxiId(103);
		taxies.add(tx3);
		
		return taxies;
	}

}
