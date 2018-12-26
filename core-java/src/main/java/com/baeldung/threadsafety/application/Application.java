package com.baeldung.threadsafety.application;

import com.baeldung.threadsafety.mathutils.MathUtils;
import com.baeldung.threadsafety.services.Counter;
import com.baeldung.threadsafety.services.ExtrinsicLockCounter;
import com.baeldung.threadsafety.services.MessageService;
import com.baeldung.threadsafety.services.ReentrantLockCounter;
import com.baeldung.threadsafety.services.ReentrantReadWriteLockCounter;
import com.baeldung.threadsafety.threads.ThreadA;
import com.baeldung.threadsafety.threads.ThreadB;
import com.baeldung.threadsafety.threads.ThreadC;
import com.baeldung.threadsafety.threads.ThreadD;
import com.baeldung.threadsafety.threads.ThreadE;
import com.baeldung.threadsafety.threads.ThreadF;
import com.baeldung.threadsafety.threads.ThreadG;
import com.baeldung.threadsafety.threads.ThreadH;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            System.out.println(MathUtils.factorial(10));
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(MathUtils.factorial(5));
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        Thread thread3 = new ThreadA();
        Thread thread4 = new ThreadB();
        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        
        ThreadC thread5 = new ThreadC();
        ThreadC thread6 = new ThreadC();
        thread5.start();
        thread6.start();
        thread5.join();
        thread6.join();

        MessageService messageService = new MessageService("Welcome to Baeldung!");
        ThreadD thread7 = new ThreadD(messageService);
        ThreadD thread8 = new ThreadD(messageService);
        thread7.start();
        thread8.start();
        thread7.join();
        thread8.join();

        Counter counter = new Counter();
        ThreadE thread9 = new ThreadE(counter);
        ThreadE thread10 = new ThreadE(counter);
        thread9.start();
        thread10.start();
        thread9.join();
        thread10.join();
        
        ExtrinsicLockCounter lockCounter = new ExtrinsicLockCounter();
        ThreadF thread11 = new ThreadF(lockCounter);
        ThreadF thread12 = new ThreadF(lockCounter);
        thread11.start();
        thread12.start();
        thread11.join();
        thread12.join();

        ReentrantLockCounter reentrantLockCounter = new ReentrantLockCounter();
        ThreadG thread13 = new ThreadG(reentrantLockCounter);
        ThreadG thread14 = new ThreadG(reentrantLockCounter);
        thread13.start();
        thread14.start();
        thread13.join();
        thread14.join();
        
        ReentrantReadWriteLockCounter reentrantReadWriteLockCounter = new ReentrantReadWriteLockCounter();
        ThreadH thread15 = new ThreadH(reentrantReadWriteLockCounter);
        ThreadH thread16 = new ThreadH(reentrantReadWriteLockCounter);
        thread15.start();
        thread16.start();
        thread15.join();
        thread16.join();
        
        Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
        Thread thread17 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        Thread thread18 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        thread17.start();
        thread18.start();
        
        Map<String,String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("1", "one");
        concurrentMap.put("2", "two");
        concurrentMap.put("3", "three");
    }
}
