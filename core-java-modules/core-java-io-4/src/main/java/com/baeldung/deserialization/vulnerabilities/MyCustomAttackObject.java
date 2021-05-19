package com.baeldung.deserialization.vulnerabilities;

import java.io.IOException;
import java.io.Serializable;

public class MyCustomAttackObject implements Serializable {
    public static void methodThatTriggersAttack() {
        try {
            Runtime.getRuntime().exec("echo \"Oh, no! I've been hacked\"");
        } catch (IOException e) {
            // handle error...
        }
    }
}
