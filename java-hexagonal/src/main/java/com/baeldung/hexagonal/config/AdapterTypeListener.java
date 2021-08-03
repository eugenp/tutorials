package com.baeldung.hexagonal.config;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeListener;
import com.google.inject.spi.TypeEncounter;

import com.baeldung.hexagonal.port.Port;
import com.baeldung.hexagonal.adapter.Adapter;

public class AdapterTypeListener implements TypeListener {
    
    final private Map<Class<?>, Class<?>> classesMapping;
    private String defaultPort;
    
    protected Object mappingOf(Class<?> clazz) {
       Class<?> mappedClass = classesMapping.get(clazz);
       try {
           return (mappedClass != null ) ? 
                  mappedClass.getConstructor().newInstance()
                  :
                  Class.forName(this.defaultPort).getConstructor().newInstance();
               
           
       } catch (Exception e){
           throw new RuntimeException(e);  
       }
    }
    
    
    public AdapterTypeListener(Map<Class<?>, Class<?>> classesMapping) {
        this.classesMapping = classesMapping;
    }
    
    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
      Class<?> clazz = typeLiteral.getRawType();
      
      while (clazz != null && clazz.isAnnotationPresent(Adapter.class)) {
        Adapter adapterAnnotation = clazz.getAnnotation(Adapter.class);
        this.defaultPort = null;
        if (adapterAnnotation.defaultPort().length() > 0 ){
            this.defaultPort = adapterAnnotation.defaultPort();
        }
        for (Field field : clazz.getDeclaredFields()) {
          if (field.isAnnotationPresent(Port.class)) {
            typeEncounter.register(new AdapterMembersInjector<T>(field, mappingOf(clazz)));
          }
        }
        clazz = clazz.getSuperclass();
      }
    }
}