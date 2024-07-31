package com.baeldung.map.hashmapcopy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.map.hashmapcopy.CopyingAHashMapToAnother;

public class CopyHashMapIntoAnotherUnitTest {
    @Test
    public void givenSourceAndTargetMapsWhenIteratedOverThenCopyingSuccess(){
        CopyingAHashMapToAnother obj = new CopyingAHashMapToAnother();
        Assert.assertEquals(generateExpectedResultMap(), obj.copyByIteration(generateSourceMap(), generateTargetMap()));
    }

    @Test
    public void givenSourceAndTargetMapsWhenUsedPutAllThenCopyingSuccess(){
        CopyingAHashMapToAnother obj = new CopyingAHashMapToAnother();
        Assert.assertEquals(generateExpectedResultMap(), obj.copyUsingPutAll(generateSourceMap(), generateTargetMap()));
    }

    @Test
    public void givenSourceAndTargetMapsWhenUsedPutIfAbsentThenCopyingSuccess(){
        CopyingAHashMapToAnother obj = new CopyingAHashMapToAnother();
        Assert.assertEquals(generateExpectedResultMap(), obj.copyUsingPutIfAbsent(generateSourceMap(), generateTargetMap()));
        Assert.assertEquals(generateExpectedResultMap(), obj.copyUsingPutIfAbsentForEach(generateSourceMap(), generateTargetMap()));
    }

    @Test
    public void givenSourceAndTargetMapsWhenUsedMapMergeThenCopyingSuccess(){
        CopyingAHashMapToAnother obj = new CopyingAHashMapToAnother();
        Assert.assertEquals(generateExpectedResultMap(), obj.copyUsingMapMerge(generateSourceMap(), generateTargetMap()));
    }

    @Test
    public void givenSourceAndTargetMapsWhenMapDifferenceUsedThenCopyingSuccess(){
        CopyingAHashMapToAnother obj = new CopyingAHashMapToAnother();
        Assert.assertEquals(generateExpectedResultMap(), obj.copyUsingGuavaMapDifference(generateSourceMap(), generateTargetMap()));
    }

    private Map<String, String> generateSourceMap(){
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("India", "Delhi");
        sourceMap.put("United States", "Washington D.C.");
        sourceMap.put("United Kingdom", "London DC");
        return sourceMap;
    }

    private Map<String, String> generateTargetMap(){
        Map<String, String> targetMap = new HashMap<>();
        targetMap.put("Zimbabwe", "Harare");
        targetMap.put("Norway", "Oslo");
        targetMap.put("United Kingdom", "London");
        return targetMap;
    }

    private Map<String, String> generateExpectedResultMap(){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("India", "Delhi");
        resultMap.put("United States", "Washington D.C.");
        resultMap.put("United Kingdom", "London");
        resultMap.put("Zimbabwe", "Harare");
        resultMap.put("Norway", "Oslo");
        return resultMap;
    }
}
