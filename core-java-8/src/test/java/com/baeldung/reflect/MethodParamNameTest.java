package com.baeldung.reflect;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import  static java.util.stream.Collectors.toList;
import org.junit.Test;

public class MethodParamNameTest {

    @Test
    public void whenGetConstructorParams_thenOk() throws NoSuchMethodException, SecurityException {
    	List<Parameter> parameters 
    		= Arrays.asList(
    				Person.class.getConstructor(String.class, String.class, Integer.class)
    					.getParameters());
    	List<String> parameterNames 
    		= parameters.stream().map(Parameter::getName).collect(toList());
    	assertThat(parameterNames)
    		.containsExactlyInAnyOrder("lastName", "firstName", "age");    
    }

    @Test
    public void whenGetMethodParams_thenOk() throws NoSuchMethodException, SecurityException {
    	List<Parameter> parameters 
    		= Arrays.asList(
    				Person.class.getMethod("setLastName", String.class).getParameters());
    	assertThat(parameters.get(0).getName()).isEqualTo("lastName");
    }
}
