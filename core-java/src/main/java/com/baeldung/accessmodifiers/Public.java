package com.baeldung.accessmodifiers;

/**
 * @author zn.wang
 */
public class Public {
    public Public() {
        SuperPublic.publicMethod(); // Available everywhere.
        SuperPublic.protectedMethod(); // Available in the same package or subclass.
        SuperPublic.defaultMethod(); // Available in the same package.
    }
}
