package com.baeldung.core.modifiers;

public class PublicOuterClass {

    public PrivateInnerClass getInnerClassInstance() {
        PrivateInnerClass myPrivateClassInstance = this.new PrivateInnerClass();
        myPrivateClassInstance.id = "ID1";
        myPrivateClassInstance.name = "Bob";
        return myPrivateClassInstance;
    }

    private class PrivateInnerClass {
        public String name;
        public String id;
    }
}
