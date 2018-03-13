package net.jcip.examples;

import java.util.*;
import java.util.concurrent.*;

/**
 * TransformingSequential
 * <p/>
 * Transforming sequential execution into parallel execution
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class TransformingSequential {

    void processSequentially(List<Element> elements) {
        for (Element e : elements)
            process(e);
    }

    void processInParallel(Executor exec, List<Element> elements) {
        for (final Element e : elements)
            exec.execute(new Runnable() {
                public void run() {
                    process(e);
                }
            });
    }

    public abstract void process(Element e);


    public <T> void sequentialRecursive(List<Node<T>> nodes,
                                        Collection<T> results) {
        for (Node<T> n : nodes) {
            results.add(n.compute());
            sequentialRecursive(n.getChildren(), results);
        }
    }

    public <T> void parallelRecursive(final Executor exec,
                                      List<Node<T>> nodes,
                                      final Collection<T> results) {
        for (final Node<T> n : nodes) {
            exec.execute(new Runnable() {
                public void run() {
                    results.add(n.compute());
                }
            });
            parallelRecursive(exec, n.getChildren(), results);
        }
    }

    public <T> Collection<T> getParallelResults(List<Node<T>> nodes)
            throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
        parallelRecursive(exec, nodes, resultQueue);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }

    interface Element {
    }

    interface Node <T> {
        T compute();

        List<Node<T>> getChildren();
    }
}

