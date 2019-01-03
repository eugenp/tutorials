package com.baeldung.stackoverflowerror;

import org.junit.Test;

/**
 * 测试：循环依赖
 */
public class CyclicDependancyManualTest {

    @Test(expected = StackOverflowError.class)
    public void whenInstanciatingClassOne_thenThrowsException() {
        try{
            ClassOne obj = new ClassOne();
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }
}
