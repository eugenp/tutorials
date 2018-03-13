package io.github.lihongjie.thread.demo1;

/**
 * Can we start a thread twice
 * 测试行程执行两次start会发生什么，
 * 执行一次run方法,然后抛出java.lang.IllegalThreadStateException
 * @author lihongjie
 *
 */
public class TestThreadTwice extends Thread {

	
	@Override
	public void run() {
		
		System.out.println("runnig...");
	}

	public static void main(String[] args) {
		TestThreadTwice t1 = new TestThreadTwice();
		t1.start();
		t1.start();
	}

}
