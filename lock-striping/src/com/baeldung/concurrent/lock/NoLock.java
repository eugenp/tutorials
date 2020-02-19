package com.baeldung.concurrent.lock;

import java.util.Map;

import com.google.common.base.Supplier;

public class NoLock extends ConcurrentAccessMap {
	
	public NoLock(Map<String, String> map) {
		super(map);
	}

	protected Supplier<?> putSupplier(int x) {
		return (()-> {
			boolean done = false;
			while(!done) {
				map.put("key" + x, "value" + x);
    			done = true;
			}
    		return null;
		});
	}
	
	protected Supplier<?> getSupplier(int x) {
		return (()-> {
			boolean done = false;
			while(!done) {
				map.get("key" + x);
    			done = true;
			}
    		return null;
		});
	}
}