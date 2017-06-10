package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class BedService {

	@Autowired
	private AmenityService amenityService;

}
