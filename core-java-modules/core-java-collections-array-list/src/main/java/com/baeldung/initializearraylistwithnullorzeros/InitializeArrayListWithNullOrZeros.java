package com.baeldung.initializearraylistwithnullorzeros;

import java.util.ArrayList;

public class InitializeArrayListWithNullOrZeros {

    public static void main(String[] args) {

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            arrayList.add(null);
        }
    }
}

