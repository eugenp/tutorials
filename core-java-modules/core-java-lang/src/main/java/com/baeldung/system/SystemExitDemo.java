package com.baeldung.system;

/**
 * Note: This class is not meant for unit-testing since it uses system
 * features at low level and that it uses 'System' exit() which will
 * exit the JVM. Also unit-tests in CI environments are not meant to
 * exit unit tests like that. But the usage below demonstrates how the
 * method can be used.
 */
public class SystemExitDemo {
    public static void main(String[] args) {
        boolean error = false;

        // do something and set error value

        if (error) {
            System.exit(1); // error case exit
        } else {
            System.exit(0); // normal case exit
        }

        // Will not do anything after exit()
    }
}
