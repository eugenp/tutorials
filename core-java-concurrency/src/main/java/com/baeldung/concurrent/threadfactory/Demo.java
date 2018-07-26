package com.baeldung.concurrent.threadfactory;

public class Demo {

	public void execute() {
		BaeldungThreadFactory factory = new BaeldungThreadFactory("BaeldungThreadFactory");
		for (int i = 0; i < 10; i++) {
			Thread t = factory.newThread(new Task());
			t.start();
		}
	}

}
