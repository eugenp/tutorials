package org.baeldung.java.streams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import org.junit.Test;

public class ThreadPoolInParallelStream {
    
    @Test
    public void giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal() 
        throws InterruptedException, ExecutionException {
        List<Long> aList = new ArrayList<>();        
        long lastNum = 1_000_000;    
        long firstNum = 1;
        
        long expectedTotal = (lastNum + firstNum) * lastNum / 2;
        
        for(long i = firstNum; i <= lastNum; i++){
            aList.add(i);
        } 
        
        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        long actualTotal = customThreadPool.submit(() -> aList.parallelStream().reduce(
            0L, (x, y) -> { 
                return x + y;
            })).get();
        
        assertEquals(expectedTotal, actualTotal);
    }
    
    @Test
    public void givenList_whenCallingParallelStream_shouldBeParallelStream(){
        List<Long> aList = new ArrayList<>();
        Stream<Long> parallelStream = aList.parallelStream();
        
        assertTrue(parallelStream.isParallel());
    }
}
