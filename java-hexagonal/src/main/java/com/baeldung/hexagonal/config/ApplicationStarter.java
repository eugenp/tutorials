package com.baeldung.hexagonal.config;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class ApplicationStarter {
    final static String DEFAULT_ENTRYPOINT = "entryPoint";
    
    Injector injector;
    
    
    void invokeEntryPoint(Class<?> applicationClass, String entryPointMethodName){
        try {
            Method entryPointMethod = applicationClass.getMethod(entryPointMethodName);
            Object appInstance = applicationClass.getConstructor().newInstance();
            entryPointMethod.invoke(appInstance);
        } catch (Exception startException ) {
            System.err.println(startException);
            System.exit(-1);
        }
    }
    void applyInjections(Map<String, String> mappings){
        
        ApplicationConfigurator config = new ApplicationConfigurator(mappings);
        this.injector = Guice.createInjector(config);
        
    }
    public <T> T getInstance(Class<T> clazz){
        return this.injector.getInstance(clazz);
    }
    static public ApplicationStarter start(Class applicationClass, Map<String, String> mappings) {
        ApplicationStarter me = new ApplicationStarter();
        me.applyInjections(mappings);
        return me;
        
    }
    
}