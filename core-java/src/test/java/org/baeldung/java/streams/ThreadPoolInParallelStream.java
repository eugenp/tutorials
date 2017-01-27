package org.baeldung.java.streams;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolInParallelStream {
    Integer total = 1784293664;
    Logger log = LoggerFactory.getLogger(ThreadPoolInParallelStream.class);
    
    @Test
    public void givenListOfInteger_calculateSumOfIntegers() throws InterruptedException, ExecutionException {
        List<Integer> aList = new ArrayList<>();        
        int listSize = 1_000_000;        
        for(int i = 1; i <= listSize; i++){
            aList.add(i);
        }        
        ForkJoinPool customThreadPool = new ForkJoinPool(5);
        Integer sum = customThreadPool.submit(() -> aList.parallelStream().reduce(
            0, (x, y) -> { 
                return x + y;
            })).get();
        
        assertEquals(total, sum);
    }
    
    @Test
    public void givenList_whenCallingParallelStream_shouldBeParallelStream(){
        List<Integer> aList = new ArrayList<>();
        Stream<Integer> parallelStream = aList.parallelStream();
        
        assertTrue(parallelStream.isParallel());
    }
}
