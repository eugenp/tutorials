package com.baeldung.guice.service.impl;

import java.util.UUID;

import com.baeldung.guice.service.DataPumpService;

public class DataPumpServiceImpl implements DataPumpService {

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}

}
