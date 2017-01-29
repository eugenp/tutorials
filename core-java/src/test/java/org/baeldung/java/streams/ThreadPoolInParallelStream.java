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
    public void giveRangeOfInts_whenSumedInParallel_shouldBeEqualToExpectedTotal() throws InterruptedException, ExecutionException {
        List<Integer> aList = new ArrayList<>();        
        int lastNum = 1_000_000;    
        int firstNum = 1;
        
        int expectedTotal = (lastNum - firstNum  + 1) * (firstNum  + lastNum ) / 2;
        
        for(int i = firstNum; i <= lastNum; i++){
            aList.add(i);
        } 
        
        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        int actualTotal = customThreadPool.submit(() -> aList.parallelStream().reduce(
            0, (x, y) -> { 
                return x + y;
            })).get();
        
        assertEquals(expectedTotal, actualTotal);
    }
    
    @Test
    public void givenList_whenCallingParallelStream_shouldBeParallelStream(){
        List<Integer> aList = new ArrayList<>();
        Stream<Integer> parallelStream = aList.parallelStream();
        
        assertTrue(parallelStream.isParallel());
    }
}
