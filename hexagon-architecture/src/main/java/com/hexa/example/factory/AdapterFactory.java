package com.hexa.example.factory;

import com.hexa.example.adapters.ReserveOrderAdapter;
import com.hexa.example.ports.ReserveOrderPort;

public class AdapterFactory {

	public static ReserveOrderPort getOrderProcessor(String type) throws Exception {
		if(type.equalsIgnoreCase("MOBILE")) {
			return new ReserveOrderAdapter();
		}else if(type.equalsIgnoreCase("WEB")) {
			return new ReserveOrderAdapter();
		}else if(type.equalsIgnoreCase("TERMINAL")) {
			throw new Exception("Mode of processor is not Available");
			
		}
		return null;
	}
}
