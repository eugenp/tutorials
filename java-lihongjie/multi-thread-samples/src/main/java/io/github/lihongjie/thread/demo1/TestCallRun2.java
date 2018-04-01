package io.github.lihongjie.thread.demo1;

/**
 * Problem if you direct call run() method
 * 出现问题：
 * t1,t2 对象会被当做正常对象而不是线程对象
 * 会发生顺序执行,t1执行完后再执行t2
 * @author lihongjie
 *
 */
public class TestCallRun2 extends Thread {

	
	@Override
	public void run() {
		for(int i=1; i < 5; i++) {
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				System.out.println(e);
			}
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		TestCallRun2 t1 = new TestCallRun2();
		TestCallRun2 t2 = new TestCallRun2();
		
		t1.run();
		t2.run();

	}

	/**
	 * output:
	 * 1
	 * 2
	 * 3
	 * 4
	 * 1
	 * 2
	 * 3
	 * 4
	 *
	 */

}
