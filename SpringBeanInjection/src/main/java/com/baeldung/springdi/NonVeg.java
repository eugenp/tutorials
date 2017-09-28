package com.baeldung.springdi;
import org.springframework.stereotype.Service;

@Service
public class NonVeg implements VegNonVeg {

	public String food() {
		return "Steaks";
	}

}
