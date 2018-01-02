package com.baeldung.ejb.tutorial;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HelloStatefulWorldTestUnitTest {

    private HelloStatefulWorldBean statefulBean;
    
    @Before
    public void setup() {
        statefulBean = new HelloStatefulWorldBean();
    }
    
    @Test
    public void whenStatefulBeanGetHelloWorldIsCalled_thenHelloStatefulWorldIsReturned() {
        String helloWorld = statefulBean.getHelloWorld();
        
        assertThat(helloWorld).isEqualTo("Hello Stateful World!");
    }
    
    @Test
    public void whenStatefulBeanGetHelloWorldIsCalled_thenHowManyTimesCounterShouldbeIncreased() {
        statefulBean.getHelloWorld();
        assertThat(statefulBean.howManyTimes()).isEqualTo(1);
        
        statefulBean.getHelloWorld();
        assertThat(statefulBean.howManyTimes()).isEqualTo(2);
    }
    
}
