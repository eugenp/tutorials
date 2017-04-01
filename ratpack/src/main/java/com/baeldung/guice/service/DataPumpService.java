package com.baeldung.guice.service;

import com.baeldung.guice.service.impl.DataPumpServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(DataPumpServiceImpl.class)
public interface DataPumpService {

	String generate();

}
