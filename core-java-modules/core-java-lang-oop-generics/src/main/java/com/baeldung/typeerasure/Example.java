package com.baeldung.typeerasure;

public class Example {

    public static  <E> boolean containsElement(E [] elements, E element){
        for (E e : elements){
            if(e.equals(element)){
                return true;
            }
        }
        return false;
    }
}
