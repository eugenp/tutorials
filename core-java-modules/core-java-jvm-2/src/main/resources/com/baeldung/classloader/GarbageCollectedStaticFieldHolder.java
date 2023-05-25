package com.baeldung.classloader;

import java.util.Scanner;

public class GarbageCollectedStaticFieldHolder {

    private static GarbageCollectedInnerObject garbageCollectedInnerObject =
        new GarbageCollectedInnerObject("Hello from a garbage collected static field");

    public void printValue(){
        System.out.println(garbageCollectedInnerObject.getMessage() );
    }
}

class GarbageCollectedInnerObject {

    private final String message;

    public GarbageCollectedInnerObject(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected void finalize() {
        System.out.println("The object is garbage now");
    }
}