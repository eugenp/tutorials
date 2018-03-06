package io.github.lihongjie.thread.demo1;

import java.util.Date;

/**
 * Created by lihongjie on 8/2/17.
 *
 */
public class TimePrinter extends Thread {

    int pauseTime;
    String name;

    public TimePrinter(int x, String n) {
        pauseTime = x;
        name = n;
    }
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(name + ":" + new Date(System.currentTimeMillis()));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {

        TimePrinter tp1 = new TimePrinter(1000, "Fast Guy");
        tp1.start();
        TimePrinter tp2 = new TimePrinter(3000, "Slow Guy");
        tp2.start();
    }
}
