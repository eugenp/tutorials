package com.baeldung.struts.test;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;


import com.baeldung.struts.CarAction;
import com.opensymphony.xwork2.ActionProxy;

public class TestCarAction extends StrutsTestCase {
   
    @Test
    public void test_givenCarOptions_WhenferrariSelected_ThenShowMessage() throws Exception {
        request.setParameter("carName", "ferrari");
        ActionProxy proxy = getActionProxy("/tutorial/car.action");
        CarAction carAction = (CarAction) proxy.getAction();
        String result = proxy.execute();
        assertEquals(result,"success");
        assertEquals(carAction.getCarMessage(), "Ferrari Fan!");
    }
  
    
    public void test_givenCarOptions_WhenbmwSelected_ThenShowMessage() throws Exception {
        request.setParameter("carName", "bmw");
        ActionProxy proxy = getActionProxy("/tutorial/car.action");
        CarAction carAction = (CarAction) proxy.getAction();
        String result = proxy.execute();
        assertEquals(result,"success");
        assertEquals(carAction.getCarMessage(), "BMW Fan!");
    }
    
    
}
