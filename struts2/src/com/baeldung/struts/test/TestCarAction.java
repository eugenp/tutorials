package com.baeldung.struts.test;

import org.apache.struts2.StrutsTestCase;

import com.baeldung.struts.CarAction;
import com.opensymphony.xwork2.ActionProxy;

public class TestCarAction extends StrutsTestCase {
    public void test_when_input_is_ferrari() throws Exception {
        request.setParameter("carName", "ferrari");
        ActionProxy proxy = getActionProxy("/tutorial/car.action");
        CarAction carAction = (CarAction) proxy.getAction();
        String result = proxy.execute();
        assertEquals(result,"success");
        assertEquals(carAction.getCarMessage(), "Ferrari Fan!");
    }
    
    public void test_when_input_is_bmw() throws Exception {
        request.setParameter("carName", "bmw");
        ActionProxy proxy = getActionProxy("/tutorial/car.action");
        CarAction carAction = (CarAction) proxy.getAction();
        String result = proxy.execute();
        assertEquals(result,"success");
        assertEquals(carAction.getCarMessage(), "BMW Fan!");
    }
    
    public void test_when_input_is_unexpected() throws Exception {
        request.setParameter("carName", "fiat");
        ActionProxy proxy = getActionProxy("/tutorial/car.action");
        CarAction carAction = (CarAction) proxy.getAction();
        String result = proxy.execute();
        assertEquals(result,"success");
        assertEquals(carAction.getCarMessage(), "please choose ferrari Or bmw");
    }
    
}
