package com.baeldung.timebaseduuid;

import com.github.f4b6a3.uuid.UuidCreator;

public class UUIDCreatorExample {

    public static void main(String[] args) {
        System.out.println("UUID Version 1: " + UuidCreator.getTimeBased());
        System.out.println("UUID Version 6: " + UuidCreator.getTimeOrdered());
        System.out.println("UUID Version 7: " + UuidCreator.getTimeOrderedEpoch());
    }
}

