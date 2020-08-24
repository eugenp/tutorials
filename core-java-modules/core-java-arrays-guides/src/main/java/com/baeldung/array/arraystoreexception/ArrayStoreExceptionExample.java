package com.baeldung.array.arraystoreexception;

public class ArrayStoreExceptionExample {

    public static void main(String[] args) {

        try {
            Object array[] = new String[5];
            array[0] = 2;
            System.out.println(array[0]);
        } catch (ArrayStoreException e) {
            e.printStackTrace();
        }

    }

}
