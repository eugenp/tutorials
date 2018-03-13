package com.habuma.spitter.mvc;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.ui.ModelMap;


public class SpittleBackingBeanInterceptorTest {
  @Test
  public void shouldPutEmptySpittleObjectInModelInPostHandle() 
      throws Exception {
    SpittleBackingBeanInterceptor interceptor = 
          new SpittleBackingBeanInterceptor();
    ModelMap model = new ModelMap();
    interceptor.postHandle(null, model);
    assertNotNull(model.get("spittle"));
  }
}
