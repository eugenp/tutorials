package io.github.lihongjie.thread.demo1;

import java.util.Date;

/**
 * Created by lihongjie on 8/2/17.
 *
 * 请注意，当使用 runnable 接口时，您不能直接创建所需类的对象并运行它；
 * 必须从 Thread 类的一个实例内部运行它。许多程序员更喜欢 runnable 接口，因为从 Thread 类继承会强加类层次。
 */
public class TimerPrinterRunnable implements Runnable {

    int pauseTime;
    String name;

    public TimerPrinterRunnable(int x, String n) {
        pauseTime = x;
        name = n;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(name + ":" + new
                        Date(System.currentTimeMillis()));
                Thread.sleep(pauseTime);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(new TimerPrinterRunnable(1000, "Fast Guy"));
        thread1.start();
        Thread thread2 = new Thread(new TimerPrinterRunnable(3000, "Fast Guy"));
        thread2.start();
    }
}
