package com.baeldung.threadpool;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountingTask extends RecursiveTask<Integer> {

    private final TreeNode node;

    CountingTask(TreeNode node) {
        this.node = node;
    }

    @Override
    protected Integer compute() {
        return node.getValue() + node.getChildren().stream()
          .map(childNode -> new CountingTask(childNode).fork())
          .mapToInt(ForkJoinTask::join)
          .sum();
    }

}
