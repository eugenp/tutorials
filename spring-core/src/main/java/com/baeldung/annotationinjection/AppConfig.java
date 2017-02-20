package com.baeldung.java_bean_injection;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.baeldung.java_bean_injection.*;

@Configuration
@PropertySource(value="classpath:inject.properties")
@Import(ImportConfig.class)
public class AppConfig {
    @Value("${contructor.arg1}") String constructorArg;
    
    @Bean
    public InjectedClass injectedClass(){
        InjectedClass ic = new InjectedClass(constructorArg);
        ic.setMyInt(10);
        ic.setTestString("test");
        return ic;
    }
    
}
