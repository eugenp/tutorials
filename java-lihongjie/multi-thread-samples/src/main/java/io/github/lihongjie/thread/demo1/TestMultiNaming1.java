package io.github.lihongjie.thread.demo1;

/**
 * 测试getName()  setName(String) and getId() method:
 * @author lihongjie
 *
 */
public class TestMultiNaming1 extends Thread {

	@Override
	public void run() {
		System.out.println("running...");
	}

	public static void main(String[] args) {
		TestMultiNaming1 t1 = new TestMultiNaming1();
		TestMultiNaming1 t2 = new TestMultiNaming1();

		System.out.println("Name of t1:" + t1.getName());
		System.out.println("Name of t2:" + t2.getName());
		System.out.println("id of t1:" + t1.getId());
		System.out.println("id of t2:" + t2.getId());
		t1.start();
		
		t1.setName("Sonoo Jaiswal");
		System.out.println("After changing name of t1:"+t1.getName());
		
		t2.start();
	}

	/**
	 * output:
	 * Name of t1:Thread-0
	 * Name of t2:Thread-1
	 * id of t1:11
	 * id of t2:12
	 * After changing name of t1:Sonoo Jaiswal
	 * running...
	 * running...
	 */

}
