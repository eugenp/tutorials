package com.baeldung.noclassdeffounderror;

import org.junit.Test;

/**
 * 测试：没有类定义异常
 */
public class NoClassDefFoundErrorUnitTest {

    @Test(expected = NoClassDefFoundError.class)
    public void givenInitErrorInClass_whenloadClass_thenNoClassDefFoundError() {
        try{
            NoClassDefFoundErrorExample sample = new NoClassDefFoundErrorExample();
            sample.getClassWithInitErrors();
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

}


