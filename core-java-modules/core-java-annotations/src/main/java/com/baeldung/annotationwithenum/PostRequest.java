package com.baeldung.annotationwithenum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface PostRequest {
    RequestContentType contentType() default RequestContentType.JSON;
    int intContentType() default 1;

    String extendedContentType() default ExtendedRequestContentType.Constants.XML_VALUE;
}
