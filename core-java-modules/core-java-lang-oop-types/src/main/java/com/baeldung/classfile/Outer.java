package com.baeldung.classfile;

import org.apache.commons.lang3.StringUtils;
import com.baeldung.classfile.HelloWorld.HelloSomeone;

public class Outer {

    // Static Nested class
    static class StaticNested {
        public String message() {
            return "This is a static Nested Class";
        }
    }

    // Non-static Nested class
    class Nested {
        public String message() {
            return "This is a non-static Nested Class";
        }
    }

    // Local class
    public String message() {
        class Local {
            private String message() {
                return "This is a Local Class within a method";
            }
        }
        Local local = new Local();
        return local.message();
    }

    // Local class within if clause
    public String message(String name) {
        if (StringUtils.isEmpty(name)) {
            class Local {
                private String message() {
                    return "This is a Local Class within if clause";
                }
            }
            Local local = new Local();
            return local.message();
        } else
            return "Welcome to " + name;
    }

    // Anonymous Inner class extending a class
    public String greet() {
        Outer anonymous = new Outer() {
            public String greet() {
                return "Running Anonymous Class...";
            }
        };
        return anonymous.greet();
    }

    // Anonymous inner class implementing an interface
    public String greet(String name) {

        HelloWorld helloWorld = new HelloWorld() {
            public String greet(String name) {
                return "Welcome to " + name;
            }

        };
        return helloWorld.greet(name);
    }

    // Anonymous inner class implementing nested interface
    public String greetSomeone(String name) {

        HelloSomeone helloSomeOne = new HelloSomeone() {
            public String greet(String name) {
                return "Hello " + name;
            }

        };
        return helloSomeOne.greet(name);
    }

    // Nested interface within a class
    interface HelloOuter {
        public String hello(String name);
    }

    // Enum within a class
    enum Color {
        RED, GREEN, BLUE;
    }
}

interface HelloWorld {

    public String greet(String name);

    // Nested class within an interface
    class InnerClass {
        public String greet(String name) {
            return "Inner class within an interface";
        }
    }

    // Nested interface within an interfaces
    interface HelloSomeone {
        public String greet(String name);
    }

    // Enum within an interface
    enum Directon {
        NORTH, SOUTH, EAST, WEST;
    }
}

enum Level {
    LOW, MEDIUM, HIGH;

}

enum Foods {

    DRINKS, EATS;

    // Enum within Enum
    enum DRINKS {
        APPLE_JUICE, COLA;
    }

    enum EATS {
        POTATO, RICE;
    }
}
