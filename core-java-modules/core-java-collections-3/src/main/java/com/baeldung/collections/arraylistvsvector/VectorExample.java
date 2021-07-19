package com.baeldung.collections.arraylistvsvector;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class VectorExample {

    public static void main(String[] args) {

        Vector<String> vector = new Vector<>();
        vector.add("baeldung");
        vector.add("Vector");
        vector.add("example");

        Enumeration e = vector.elements();
        while(e.hasMoreElements()){
            System.out.println(e.nextElement());
        }

        Iterator<String> iterator = vector.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
