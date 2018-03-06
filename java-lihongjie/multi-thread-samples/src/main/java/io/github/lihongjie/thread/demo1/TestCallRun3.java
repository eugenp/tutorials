package io.github.lihongjie.thread.demo1;

/**
 * 正常调用 start()方法而不是run()方法
 * 每个线程对象会交替执行
 * @author lihongjie
 *
 */
public class TestCallRun3 extends Thread {

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
		
		TestCallRun3 t1 = new TestCallRun3();
		TestCallRun3 t2 = new TestCallRun3();
		
		t1.start();
		t2.start();
	}

}
