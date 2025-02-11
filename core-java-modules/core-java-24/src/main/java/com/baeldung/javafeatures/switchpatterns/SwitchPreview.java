package com.baeldung.java.javafeatures;
import java.util.Random;

public class SwitchPreview {
     
    void primitiveTypePatternExample() {
        Random r=new Random();
        switch (r.nextInt()) {
            case 1 -&gt; System.out.println("int is 1");
            case int i when i &gt; 1 &amp;&amp; i &lt; 100 -&gt; System.out.println("int is greater than 1 and less than 100");
            default -&gt; System.out.println("int is greater or equal to 100");
        }
    }
}
