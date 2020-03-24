package com.baeldung.guice.service;

import com.baeldung.guice.service.impl.DataPumpServiceImpl;

public class ServiceFactory {

	private static DataPumpService instance;

	public static void setInstance(DataPumpService dataPumpService) {
		instance = dataPumpService;
	}

	public static DataPumpService getInstance() {
		if (instance == null) {
			return new DataPumpServiceImpl();
		}
		return instance;
	}

}
