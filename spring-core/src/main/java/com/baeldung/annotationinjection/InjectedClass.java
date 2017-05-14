package com.baeldung.java_bean_injection;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import com.baeldung.java_bean_injection.*;


public class InjectedClass {
  private int myInt;
  private String testString;
  String myConstructorArg; 
  
  
  @Autowired
  @Qualifier("autobean")
  AutowireObject obj; 
  
  public InjectedClass(String constArg){
      this.myConstructorArg = constArg;
  }
  
  public void setMyInt(int myInt){
      this.myInt = myInt;
  }
  
  public void setTestString(String testString){
      this.testString = testString;
  }
  
  public int getMyInt(){
      return this.myInt;
  }
  
  public String getTestString(){
      return this.testString;
  }
    
}
