package com.baeldung.samples.endpoints;

public interface Activator<T> {
	
	public void handleMessage(T input);

}
