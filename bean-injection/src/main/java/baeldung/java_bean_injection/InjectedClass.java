package baeldung.java_bean_injection;

import baeldung.java_bean_injection.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

public class InjectedClass {
    long myConstructorLong;
    int myConstructorDouble;
    String myConstructorString;
    ConstructorObject constructorObject;
   
    private InjectedObject injectedObject;
    private String myString;
    private int myInt;
    
    @Autowired
    WiredObject wiredObject;

    
    public InjectedClass(long constructorLong, int myConstructorDouble, String myConstructorString,ConstructorObject constructorObject){
        this.myConstructorLong = constructorLong;
        this.myConstructorDouble = myConstructorDouble;
        this.myConstructorString = myConstructorString;
        this.constructorObject = constructorObject;
    }
    

    public void setInjectedObject(InjectedObject injectedObject){
        this.injectedObject = injectedObject;
    }
    
    public InjectedObject getInjectedObject(){
        return this.injectedObject;
    }
    
    public void setMyString(String myString){
        this.myString = myString;
    }
    
    public String getMyString(){
        return this.myString;
    }
    
    public void setMyInt(int myInt){
        this.myInt = myInt;
    }
    
    public int getMyInt(){
        return this.myInt;
    }
    
}
