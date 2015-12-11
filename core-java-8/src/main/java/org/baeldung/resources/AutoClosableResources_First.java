package org.baeldung.resources;

public class AutoClosableResources_First implements AutoCloseable {

	public AutoClosableResources_First() {
		System.out.println("Constructor -> AutoClosableResources_First");
	}
	public void doSomething() throws Exception {
		throw new Exception("Failed in doSomething");
	}
	@Override
	public void close() throws Exception {
		throw new Exception("Failed in close");
	}

}
