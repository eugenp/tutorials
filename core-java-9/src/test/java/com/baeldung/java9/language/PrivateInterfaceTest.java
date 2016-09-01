package com.baeldung.java9.language;

import com.baeldung.java9.language.PrivateInterface;
import org.junit.Test;

public class PrivateInterfaceTest {

    @Test
    public void test() {
        PrivateInterface piClass = new PrivateInterface() {
        };
        piClass.check();
    }

}
