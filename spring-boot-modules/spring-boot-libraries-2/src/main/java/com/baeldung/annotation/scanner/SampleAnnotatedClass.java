package com.baeldung.annotation.scanner;

@SampleAnnotation(name = "annotatedClass")
public class SampleAnnotatedClass {

    @SampleAnnotation(name = "annotatedMethod")
    public void annotatedMethod() {
        //Do something
    }

    public void notAnnotatedMethod() {
        //Do something
    }
}
