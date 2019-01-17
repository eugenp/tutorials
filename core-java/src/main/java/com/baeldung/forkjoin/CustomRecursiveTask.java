package com.baeldung.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 * RecursiveTask : 有返回结果的任务
 *
 * @author zn.wang
 */
public class CustomRecursiveTask extends RecursiveTask<Integer> {

    private int[] arr;

    private static final int THRESHOLD = 20;

    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {

        if (arr.length > THRESHOLD) {

            int sum = 0;
            for (CustomRecursiveTask customRecursiveTask : ForkJoinTask.invokeAll(createSubtasks())) {
                int join = customRecursiveTask.join();
                sum += join;
            }
            return sum;

        } else {
            return processing(arr);
        }
    }

    private Collection<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2)));
        dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return dividedTasks;
    }

    private Integer processing(int[] arr) {
        int sum = 0;
        for (int a : arr) {
            if (a > 10 && a < 27) {
                int i = a * 10;
                sum += i;
            }
        }
        return sum;
    }
}
