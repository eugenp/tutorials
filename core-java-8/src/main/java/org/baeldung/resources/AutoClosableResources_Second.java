package org.baeldung.resources;

public class AutoClosableResources_Second implements AutoCloseable {
	
	public AutoClosableResources_Second() {
		System.out.println("Constructor -> AutoClosableResources_Second");
	}
	
	public void doSomething() {
		System.out.println("Something -> AutoClosableResources_Second");
	}
	@Override
    public void close() throws Exception {
		System.out.println("Closed AutoClosableResources_Second");
	}
}
