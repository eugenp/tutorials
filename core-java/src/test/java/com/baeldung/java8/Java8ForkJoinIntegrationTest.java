package com.baeldung.java8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.forkjoin.CustomRecursiveAction;
import com.baeldung.forkjoin.CustomRecursiveTask;
import com.baeldung.forkjoin.util.PoolUtil;

/**
 * 测试：fork-join集成
 */
public class Java8ForkJoinIntegrationTest {

    private int[] arr;
    private CustomRecursiveTask customRecursiveTask;

    @Before
    public void init() {
        Random random = new Random();
        arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(35);
        }
        customRecursiveTask = new CustomRecursiveTask(arr);
    }

    @Test
    public void callPoolUtil_whenExistsAndExpectedType_thenCorrect() {
        ForkJoinPool forkJoinPool = PoolUtil.forkJoinPool;
        ForkJoinPool forkJoinPoolTwo = PoolUtil.forkJoinPool;

        assertNotNull(forkJoinPool);
        assertEquals(2, forkJoinPool.getParallelism());
        assertEquals(forkJoinPool, forkJoinPoolTwo);
    }

    @Test
    public void callCommonPool_whenExistsAndExpectedType_thenCorrect() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        ForkJoinPool commonPoolTwo = ForkJoinPool.commonPool();

        assertNotNull(commonPool);
        assertEquals(commonPool, commonPoolTwo);
    }

    @Test
    public void executeRecursiveAction_whenExecuted_thenCorrect() {

        CustomRecursiveAction myRecursiveAction = new CustomRecursiveAction("ddddffffgggghhhh");
        ForkJoinPool.commonPool().invoke(myRecursiveAction);

        assertTrue(myRecursiveAction.isDone());

    }

    /**
     * @see {https://blog.csdn.net/guhong5153/article/details/71247266} 案例1，将ThreadPoolExecutor线程池execute和submit方法的区别。
     * @see {https://www.jianshu.com/p/99e5a66f0482} 案例2，这里讲的是ForkJoinPool中的方法.
     * @see {https://www.cnblogs.com/shijiaqi1066/p/4631466.html} 案例3，这里讲的是ForkJoinPool中的方法，讲的非常详细。
     */
    @Test
    public void executeRecursiveTask_whenExecuted_thenCorrect() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        forkJoinPool.execute(customRecursiveTask);
        int result = customRecursiveTask.join();
        System.out.println("result:{}" + result);
        assertTrue(customRecursiveTask.isDone());

        forkJoinPool.submit(customRecursiveTask);
        int resultTwo = customRecursiveTask.join();
        System.out.println("resultTwo:{}" + resultTwo);
        assertTrue(customRecursiveTask.isDone());
    }

    /**
     * fork/join原理测试
     */
    @Test
    public void executeRecursiveTaskWithFJ_whenExecuted_thenCorrect() {
        CustomRecursiveTask customRecursiveTaskFirst = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskSecond = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskLast = new CustomRecursiveTask(arr);

        customRecursiveTaskFirst.fork();
        customRecursiveTaskSecond.fork();
        customRecursiveTaskLast.fork();
        int result = 0;
        result += customRecursiveTaskLast.join();
        result += customRecursiveTaskSecond.join();
        result += customRecursiveTaskFirst.join();
        System.out.println("result:{}" + result);

        assertTrue(customRecursiveTaskFirst.isDone());
        assertTrue(customRecursiveTaskSecond.isDone());
        assertTrue(customRecursiveTaskLast.isDone());
        assertTrue(result != 0);
    }

}
