package com.baeldung.javafeatures.switchpatterns;
import java.util.Random;

public class SwitchPreview {

    void primitiveTypePatternExample() {
        Random r=new Random();
        switch (r.nextInt()) {
            case 1 -> System.out.println("int is 1");
            case int i when i > 1 && i < 100 -> System.out.println("int is greater than 1 and less than 100");
            default -> System.out.println("int is greater or equal to 100");
        }
    }
}
