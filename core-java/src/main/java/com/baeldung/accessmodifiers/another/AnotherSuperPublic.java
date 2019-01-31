package com.baeldung.accessmodifiers.another;

import com.baeldung.accessmodifiers.SuperPublic;

/**
 * @author zn.wang
 */
public class AnotherSuperPublic {
    public AnotherSuperPublic() {
        SuperPublic.publicMethod(); // Available everywhere. Let's note different package.
    }
}
