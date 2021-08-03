package com.baeldung.hexagonal.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import  com.google.inject.matcher.Matchers;

import java.util.Map;
import java.util.HashMap;

import com.baeldung.hexagonal.adapter.Adapter;

public class ApplicationConfigurator extends AbstractModule{
    
    private Map<Class<?>, Class<?>> classesMapping;
    
    private void buildClassesMapping(final Map<String, String> classesMappingAsStrings){
        this.classesMapping = new HashMap< Class<?>, Class<?> >();
        for ( Map.Entry<String, String> clazzPairString : classesMappingAsStrings.entrySet() ){
            try{
                String key = clazzPairString.getKey();
                String value = clazzPairString.getValue();
                Class<?> keyClass = Class.forName(key);
                Class<?> valueClass = Class.forName(value);
                classesMapping.put(keyClass, valueClass);
                
            } catch (ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }
    
    public ApplicationConfigurator(final Map<String, String> classesMappingAsStrings){
        this.buildClassesMapping(classesMappingAsStrings);
    }
    
    @Override
    protected void configure() {
        bindListener(Matchers.any(), 
                     new AdapterTypeListener(this.classesMapping));
        
    }
}