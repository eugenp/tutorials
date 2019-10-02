package com.baeldung.accessmodifiers;

//Only public or default access modifiers are permitted
public class SuperPublic {
    // Always available from anywhere
    static public void publicMethod() {
        System.out.println(SuperPublic.class.getName() + " publicMethod()");
    }
    
    // Available within the same package
    static void defaultMethod() {
        System.out.println(SuperPublic.class.getName() + " defaultMethod()");
    }
    
    // Available within the same package and subclasses
    static protected void protectedMethod() {
        System.out.println(SuperPublic.class.getName() + " protectedMethod()");
    }
    
    // Available within the same class only
    static private void privateMethod() {
        System.out.println(SuperPublic.class.getName() + " privateMethod()");
    }
    
    // Method in the same class = has access to all members within the same class
    private void anotherPrivateMethod() {
        privateMethod();
        defaultMethod();
        protectedMethod();
        publicMethod(); // Available in the same class only.
    }
}

// Only public or default access modifiers are permitted
class SuperDefault {
    public void publicMethod() {
        System.out.println(this.getClass().getName() + " publicMethod()");
    }
}
