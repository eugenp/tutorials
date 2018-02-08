package com.baeldung.system;

import java.util.HashMap;
import java.util.Map;

public class SystemEnvDemo {
    public static void main(String[] args) {
        Map<String, String> envVariables = System.getenv(); // access collection

        // access each entry in the collection
        for (HashMap.Entry<String, String> entry: envVariables.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        System.out.println("PATH has value: " + System.getenv("PATH"));
    }
}
