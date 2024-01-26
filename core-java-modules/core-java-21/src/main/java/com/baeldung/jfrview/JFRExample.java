package com.baeldung.jfrview;

import java.util.ArrayList;
import java.util.List;

public class JFRExample {
    public static void main(String[] args) {
        JFRExample se = new JFRExample();
        se.insertToList(new ArrayList<>());
    }

    private void insertToList(List<Object> list) {
        try {
            while (true) {
                list.add(new Object());
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Out of Memory. Exiting");
        }
    }
}
