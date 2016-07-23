package com.baeldung.cdi.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target( {ElementType.METHOD, ElementType.TYPE } )
@Retention(RetentionPolicy.RUNTIME )
public @interface Audited {}
