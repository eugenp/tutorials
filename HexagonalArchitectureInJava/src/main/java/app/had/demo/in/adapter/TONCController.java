package app.had.demo.in.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.had.demo.domain.Taxi;
import app.had.demo.domain.TripDetails;
import app.had.demo.in.port.TONCService;

@RestController
public class TONCController {
	
	@Autowired
	private TONCService toncService;
	
	@GetMapping("/query/{pickupAddress}/{dropAddress}")
	public ResponseEntity<List<Taxi>> findTaxies(@PathVariable String pickupAddress, @PathVariable String dropAddress ) {
		return ResponseEntity.accepted().body(toncService.getTaxies(pickupAddress, dropAddress));
	}
	
	@PostMapping("/booktaxi")
	public ResponseEntity<Object> bookTaxi(@RequestBody TripDetails tripDetails) {
		return ResponseEntity.accepted().body(toncService.bookTaxi(tripDetails));
	}
	
	@PostMapping("/starttrip")
	public ResponseEntity<Object> startTrip(@RequestBody TripDetails tripDetails) {
		return ResponseEntity.accepted().body(toncService.startTrip(tripDetails));
	}
	
	@PostMapping("/endtrip")
	public ResponseEntity<Object> endTrip(@RequestBody TripDetails tripDetails) {
		return ResponseEntity.accepted().body(toncService.endTrip(tripDetails));
	}
}
