package com.baeldung.accessmodifiers.another;

import com.baeldung.accessmodifiers.SuperPublic;

public class AnotherSubClass extends SuperPublic {
    public AnotherSubClass() {
        SuperPublic.publicMethod(); // Available everywhere.
        SuperPublic.protectedMethod(); // Available in subclass. Let's note different package.
    }
}
