package com.baeldung.threadsafety.application;

import com.baeldung.threadsafety.mathutils.MathUtils;
import com.baeldung.threadsafety.callables.CounterCallable;
import com.baeldung.threadsafety.callables.ExtrinsicLockCounterCallable;
import com.baeldung.threadsafety.callables.MessageServiceCallable;
import com.baeldung.threadsafety.callables.ReentranReadWriteLockCounterCallable;
import com.baeldung.threadsafety.callables.ReentrantLockCounterCallable;
import com.baeldung.threadsafety.services.Counter;
import com.baeldung.threadsafety.services.ExtrinsicLockCounter;
import com.baeldung.threadsafety.services.MessageService;
import com.baeldung.threadsafety.services.ReentrantLockCounter;
import com.baeldung.threadsafety.services.ReentrantReadWriteLockCounter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        new Thread(() -> {
            System.out.println(MathUtils.factorial(10));
        }).start();
        new Thread(() -> {
            System.out.println(MathUtils.factorial(5));
        }).start();
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        MessageService messageService = new MessageService("Welcome to Baeldung!");
        Future<String> future1 = (Future<String>) executorService.submit(new MessageServiceCallable(messageService));
        Future<String> future2 = (Future<String>) executorService.submit(new MessageServiceCallable(messageService));
        System.out.println(future1.get());
        System.out.println(future2.get());
        
        Counter counter = new Counter();
        Future<Integer> future3 = (Future<Integer>) executorService.submit(new CounterCallable(counter));
        Future<Integer> future4 = (Future<Integer>) executorService.submit(new CounterCallable(counter));
        System.out.println(future3.get());
        System.out.println(future4.get());
        
        ExtrinsicLockCounter extrinsicLockCounter = new ExtrinsicLockCounter();
        Future<Integer> future5 = (Future<Integer>) executorService.submit(new ExtrinsicLockCounterCallable(extrinsicLockCounter));
        Future<Integer> future6 = (Future<Integer>) executorService.submit(new ExtrinsicLockCounterCallable(extrinsicLockCounter));
        System.out.println(future5.get());
        System.out.println(future6.get());
        
        ReentrantLockCounter reentrantLockCounter = new ReentrantLockCounter();
        Future<Integer> future7 = (Future<Integer>) executorService.submit(new ReentrantLockCounterCallable(reentrantLockCounter));
        Future<Integer> future8 = (Future<Integer>) executorService.submit(new ReentrantLockCounterCallable(reentrantLockCounter));
        System.out.println(future7.get());
        System.out.println(future8.get());
        
        ReentrantReadWriteLockCounter reentrantReadWriteLockCounter = new ReentrantReadWriteLockCounter();
        Future<Integer> future9 = (Future<Integer>) executorService.submit(new ReentranReadWriteLockCounterCallable(reentrantReadWriteLockCounter));
        Future<Integer> future10 = (Future<Integer>) executorService.submit(new ReentranReadWriteLockCounterCallable(reentrantReadWriteLockCounter));
        System.out.println(future9.get());
        System.out.println(future10.get());
        
        Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
        Thread thread11 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        Thread thread12 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        thread11.start();
        thread12.start();
        
        Map<String,String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("1", "one");
        concurrentMap.put("2", "two");
        concurrentMap.put("3", "three");
    }
}
