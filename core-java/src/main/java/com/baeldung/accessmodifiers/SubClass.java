package com.baeldung.accessmodifiers;

/**
 * @author zn.wang
 */
public class SubClass extends SuperPublic {
    public SubClass() {
        SuperPublic.publicMethod(); // Available everywhere.
        SuperPublic.protectedMethod(); // Available in the same package or subclass.
        SuperPublic.defaultMethod(); // Available in the same package.
    }
}
