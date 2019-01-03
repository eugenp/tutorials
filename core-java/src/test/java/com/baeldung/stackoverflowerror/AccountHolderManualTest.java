package com.baeldung.stackoverflowerror;

import org.junit.Test;

/**
 * 测试：栈溢出错误(想想为什么，这种情况会堆栈溢出异常呢？？？)
 */
public class AccountHolderManualTest {

    @Test(expected = StackOverflowError.class)
    public void whenInstanciatingAccountHolder_thenThrowsException() {
        try{
            AccountHolder holder = new AccountHolder();
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }
}
