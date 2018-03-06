package com.insightsource.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest {

    public static void main(String[] args) {
        int num = 20;

        List<FutureTask<Date>> list = new ArrayList<FutureTask<Date>>(num);


        for (int i = 0; i < num; i++)    //java.lang.Runnable has no return value.
        {
            Callable<Date> thread = new Callable<Date>() {
                public Date call() {
                    return new Date();
                }
            };

            FutureTask<Date> task = new FutureTask<Date>(thread);
            Thread t = new Thread(task);
            t.start();
            list.add(task);
        }


        for (int i = 0; i < num; i++) {
            try {
                FutureTask<Date> ft = list.get(i);
                System.out.println(ft.get().getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
