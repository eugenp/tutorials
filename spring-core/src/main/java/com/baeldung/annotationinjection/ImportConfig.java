package com.baeldung.java_bean_injection;

import org.springframework.context.annotation.*;

import com.baeldung.java_bean_injection.*;

@Configuration
public class ImportConfig {
    
    @Bean(name="autobean")
    public AutowireObject autowireObject(){
        return new AutowireObject();
    }
    
    
    @Bean(name="autobean2")
    public AutowireObject autowireObject2(){
        return new AutowireObject();
    }
}
