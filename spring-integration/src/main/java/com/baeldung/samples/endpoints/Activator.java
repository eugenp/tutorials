package com.baeldung.samples.endpoints;

interface Activator<T> {
	
	public void handleMessage(T input);

}
