package src.main.java.com.baeldung.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueImplExamples {
    public static void main(String[] args){

        int INIT_CAPACITY = 10;

        //creation of ArrayBlockingQueue with initial capacity of 10 and fairness policy set to true.
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(INIT_CAPACITY, true);

        //creation of LinkedBlockingQueue with capacity defaults to Integer.MAX_VALUE
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
    }
}
