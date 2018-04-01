package io.github.lihongjie.thread.demo1;

/**
 * 测试currentThread() method
 * @author lihongjie
 *
 */
public class TestMultiNaming2 extends Thread {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());  
	}

	public static void main(String[] args) {

		TestMultiNaming2 t1=new TestMultiNaming2();
		TestMultiNaming2 t2=new TestMultiNaming2();

		t1.start();
		t2.start();

	}

	/**
	 * Thread-1
	 * Thread-0
	 *
	 */

}
