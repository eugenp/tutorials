package com.habuma.spitter.mvc;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.habuma.spitter.domain.Spittle;

public class SpittleBackingBeanInterceptor 
    implements WebRequestInterceptor {

  public void afterCompletion(WebRequest webRequest,
          Exception arg1) throws Exception {    
  }

  public void postHandle(WebRequest webRequest, ModelMap model)
          throws Exception {  
    // TODO: Maybe should only do this if the user is logged in
    if(model != null) {
      model.addAttribute(new Spittle());
    }
  }

  public void preHandle(WebRequest webRequest) throws Exception {}

}
