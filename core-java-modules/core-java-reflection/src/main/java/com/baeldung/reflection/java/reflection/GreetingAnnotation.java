package com.baeldung.java.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class GreetingAnnotation {
   
    private static final String ANNOTATION_METHOD = "annotationData";
    private static final String ANNOTATION_FIELDS = "declaredAnnotations";
    private static final String ANNOTATIONS = "annotations";

    public static void main(String ...args) {
        Greeter greetings = Greetings.class.getAnnotation(Greeter.class);
        System.err.println("Hello there, " + greetings.greet() + " !!");

        Greeter targetValue = new DynamicGreeter("Good evening");
        //alterAnnotationValueJDK8(Greetings.class, Greeter.class, targetValue);
        alterAnnotationValueJDK7(Greetings.class, Greeter.class, targetValue);
        
        greetings = Greetings.class.getAnnotation(Greeter.class);
        System.err.println("Hello there, " + greetings.greet() + " !!");
    }
    
    @SuppressWarnings("unchecked")
    public static void alterAnnotationValueJDK8(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
        try {
            Method method = Class.class.getDeclaredMethod(ANNOTATION_METHOD, null);
            method.setAccessible(true);

            Object annotationData = method.invoke(targetClass);
            
            Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
            annotations.setAccessible(true);

            Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
            map.put(targetAnnotation, targetValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void alterAnnotationValueJDK7(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
        try {            
            Field annotations = Class.class.getDeclaredField(ANNOTATIONS);
            annotations.setAccessible(true);

            Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(targetClass);
            System.out.println(map);
            map.put(targetAnnotation, targetValue);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
