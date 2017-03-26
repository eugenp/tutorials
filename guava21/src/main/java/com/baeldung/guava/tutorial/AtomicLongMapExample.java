package com.baeldung.guava.tutorial;

import com.google.common.util.concurrent.AtomicLongMap;

public class AtomicLongMapExample {

    private AtomicLongMap<String> atomicLongMap;

    public AtomicLongMapExample(){
        atomicLongMap = AtomicLongMap.create();
    }


    public void addKeys(){
        atomicLongMap.addAndGet("apple",250);
        atomicLongMap.addAndGet("bat",350);
        atomicLongMap.addAndGet("cat",450);
        atomicLongMap.addAndGet("dog",550);
    }

    public static void main(String[] args){
        AtomicLongMapExample atomicLongMapExample = new AtomicLongMapExample();
        atomicLongMapExample.addKeys();

        System.out.println(atomicLongMapExample.atomicLongMap.get("2"));
    }

}
