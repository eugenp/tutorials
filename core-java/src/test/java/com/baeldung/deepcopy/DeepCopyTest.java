package com.baeldung.deepcopy;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import com.google.gson.Gson;

public class DeepCopyTest {

    @Test
    public void comparePerformaceOfDifferentImplementations() throws CloneNotSupportedException {
        Address address1 = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            User primeMinisterClone = (User) SerializationUtils.clone(pm);
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);

        start = System.currentTimeMillis();
        Gson gson = new Gson();
        for (int i = 0; i < 1000000; i++) {
            User primeMinisterClone = gson.fromJson(gson.toJson(pm), User.class);
        }
        end = System.currentTimeMillis();

        System.out.println(end - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            User primeMinisterClone = new User(pm);
        }
        end = System.currentTimeMillis();

        System.out.println(end - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            User primeMinisterClone = (User) pm.clone();
        }
        end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
