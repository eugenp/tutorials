package com.habuma.spitter.method;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.habuma.spitter.domain.Spittle;

@Component
public class SecuredClass implements SecuredInterface {

  public void unsecuredMethod() {
    
  }
  
  @Secured("ROLE_SPITTER")
  public void securedMethod() {
  }
  
  @PreAuthorize("hasRole('ROLE_SPITTER') and #amount > 10")
  public void preAuthorizeSecuredMethod(int amount) {
  }
  
  @PreAuthorize("#input.length() <= 3")
  @PostAuthorize("returnObject.length() == #input.length()")
  public String preAndPostAuthorizedMethod(String input) {
    return "foo";
  }
  
  public Spittle postAuthorizedSpittleMethod(Spittle spittle) {
    return spittle;
  }
  
  public Spittle getSpittleById(long id) {
    return null;
  }
  
  @Sensitive
  public void topSecretMethod(String x) {
    
  }
}
