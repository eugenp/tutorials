package io.github.lihongjie.thread.demo1;

public class TestCallRun extends Thread {

	
	@Override
	public void run() {
		System.out.println("running...");
	}

	public static void main(String[] args) {
		TestCallRun t1 = new TestCallRun();
		t1.run();//fine,but does not start a separate call stack
		
	}

}
