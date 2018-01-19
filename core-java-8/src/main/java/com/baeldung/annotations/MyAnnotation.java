package com.baeldung.annotations;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@interface MyAnnotation {

}