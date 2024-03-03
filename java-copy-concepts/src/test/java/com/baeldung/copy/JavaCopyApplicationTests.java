package com.baeldung.copy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaCopyApplicationTests {

    @Test
    void whenShallowCopy_thenSuccessful() throws CloneNotSupportedException {
        JavaCopyApplication.Item item = new JavaCopyApplication.Item("mac", 15);
        JavaCopyApplication.Shop s1 = new JavaCopyApplication.Shop("shop-1", item);
        JavaCopyApplication.Shop s2 = (JavaCopyApplication.Shop) s1.clone();

        Assertions.assertEquals(s1.getName(), s2.getName());
        Assertions.assertEquals(s1.getItem(), s2.getItem());
        System.out.println("Shallow Copy Tests Passed");
    }

    @Test
    void whenDeepCopy_thenSuccessful() {
        JavaCopyApplication.Item item = new JavaCopyApplication.Item("mac", 15);
        JavaCopyApplication.Shop s1 = new JavaCopyApplication.Shop("shop-1", item);
        JavaCopyApplication.Shop s2 = new JavaCopyApplication.Shop(s1);

        Assertions.assertEquals(s1.getName(), s2.getName());
        Assertions.assertNotEquals(s1.getItem(), s2.getItem());
        System.out.println("Deep Copy Tests Passed");
    }

}
