package com.baeldung.quarkus;

import org.jboss.logging.Logger;
import io.quarkus.funqy.Funq;

public class MyFunctions {
    private static final Logger log = Logger.getLogger(MyFunctions.class);
    @Funq("GreetUser")
    public String fun(FunInput input) {
        log.info("Function Triggered");
        String name = input != null ? input.name : "Funqy";
        return String.format("Hello %s!", name);
    }
    public static class FunInput {
        public String name;
        public FunInput() { }
        public FunInput(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
