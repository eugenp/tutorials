package com.baeldung.exampleforjadx;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExampleForJadx {

    static final String fileName = "/exampleforjadx/HelloWorld.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ExampleForJadxUtil jadxTargetUtil = new ExampleForJadxUtil();
        String greetings = jadxTargetUtil.resourceFileReader(fileName);
        String capitalizedGreetings = jadxTargetUtil.capitalizeString(greetings);
        System.out.println(capitalizedGreetings);
    }
}
