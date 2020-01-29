package com.baeldung.hexagonpattern.factory;

import com.baeldung.hexagonpattern.adapters.ExtendMaterialAdapter;
import com.baeldung.hexagonpattern.ports.ExtendMaterialPort;

public class AdapterFactory {

	public static ExtendMaterialPort getOrderProcessor(String orderType) throws Exception {
		if (orderType.equalsIgnoreCase("MOBILE")) {
			return new ExtendMaterialAdapter();
		} else if (orderType.equalsIgnoreCase("WEB")) {
			return new ExtendMaterialAdapter();
		} else if (orderType.equalsIgnoreCase("COUNTER")) {
			throw new Exception("Since it is a holiday, extending the book from  COUNTER is not possible ");
		}
		return null;
	}

}
