package com.baeldung.outofmemoryerror;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class OutOfMemoryGCLimitExceed {

    public static final Random RANDOM = new Random();

    public static void addRandomDataToList() {
        List<String> dataList = new LinkedList<>();
        while (true) {
            dataList.add(String.valueOf(RANDOM.nextInt()));
        }
    }

    public static void main(String[] args) {
        OutOfMemoryGCLimitExceed.addRandomDataToList();
    }
}
