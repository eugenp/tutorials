package io.github.lihongjie.thread.demo1;

public class TestJoinMethod2 extends Thread {

	@Override
	public void run() {
		for(int i = 1; i <= 5; i++) {
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				System.out.println(e);
			}
			
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		TestJoinMethod2 t1 = new TestJoinMethod2();
		TestJoinMethod2 t2 = new TestJoinMethod2();
		TestJoinMethod2 t3 = new TestJoinMethod2();
		t1.start();
		try {
			t1.join(1500);
		} catch(Exception e) {
			System.out.println(e);
		}
		t2.start();
		t3.start();
	}
	/**
	 * output:
	 * 1
	 * 2
	 * 3
	 * 1
	 * 1
	 * 4
	 * 2
	 * 2
	 * 5
	 * 3
	 * 3
	 * 4
	 * 4
	 * 5
	 * 5
	 *
	 */

}
