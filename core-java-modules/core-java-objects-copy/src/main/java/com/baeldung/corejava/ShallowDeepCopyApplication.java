package com.baeldung.corejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.corejava.deepcopy.DeepCopyExample;
import com.baeldung.corejava.shallowcopy.ShallowCopyExample;

@SpringBootApplication
public class ShallowDeepCopyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowDeepCopyApplication.class,args);
        System.out.println("Shallow copy example output");
        ShallowCopyExample.shallowCopyExample();
        System.out.println("Deep copy example output");
        DeepCopyExample.deepCopyExample();
    }

}
