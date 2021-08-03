package com.baeldung.hexagonal.config;

import java.lang.reflect.Field;
import com.google.inject.MembersInjector;


public class AdapterMembersInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final Object value;

    public AdapterMembersInjector(Field field, Object value) {
      this.field = field;
      this.value = value;
      field.setAccessible(true);
    } 

    public void injectMembers(T t) {
      try {
        field.set(t, value);
        
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }